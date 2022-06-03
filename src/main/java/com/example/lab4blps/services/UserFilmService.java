package com.example.lab4blps.services;

import com.example.lab4blps.models.Films;
import com.example.lab4blps.models.UserFilm;
import com.example.lab4blps.repositories.FilmsRepo;
import com.example.lab4blps.repositories.UserFilmRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserFilmService {
    @Autowired
    private UserFilmRepo userFilmRepo;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void addFilmToUser(String userId, Integer filmId) {
        UserFilm userFilm = new UserFilm();
        userFilm.setFilmId(filmId);
        userFilm.setUserId(userId);
        userFilmRepo.save(userFilm);
    }
}
