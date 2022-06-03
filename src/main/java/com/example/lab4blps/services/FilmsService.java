package com.example.lab4blps.services;


import com.example.lab4blps.exception.ResourceNotFoundException;
import com.example.lab4blps.models.Films;
import com.example.lab4blps.repositories.FilmsRepo;
import com.example.lab4blps.repositories.GenresRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmsService {
    @Autowired
    private GenresRepo genresRepo;
    @Autowired
    private FilmsRepo filmRepo;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserFilmService userFilmService;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public List<Films> getAllFilms() {
        return filmRepo.findAll();
    }

    public String getSelectFilm(Integer userId, Integer filmId) {
        Films film = filmRepo.findFilmsById(filmId);
        if (film == null) {
            throw new ResourceNotFoundException("Данный фильм не найден в базе данных");
        }

        transactionTemplate.execute(
                status -> {
                    if (film.getCost() != 0) {
                        cardService.modifyCardMoneyIfExist(userId, film);
                    }

                    userFilmService.addFilmToUser(userId, film.getId());
                    return film;
                }
                );

        return film.getToken();
    }

    @Scheduled(fixedRate = 60000)
    @Async
    public void informationAboutDB() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        log.info("Amount of films :" + filmRepo.count());
        log.info("Amount of genres :" + genresRepo.count());
        log.info("End collecting information! Time: " + formatter.format(new Date()));
    }
}
