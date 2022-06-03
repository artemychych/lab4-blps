package com.example.lab4blps.delegators;

import com.example.lab4blps.models.Films;
import com.example.lab4blps.services.FilmsService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("checkIfFilmExist")
@RequiredArgsConstructor
public class CheckIfFilmExist implements JavaDelegate {
    private final FilmsService filmsService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer filmId = Integer.parseInt(delegateExecution.getVariable("film_id").toString());
        delegateExecution.setVariable("film_id", filmId);
        delegateExecution.setVariable("checkedForExisting", filmsService.isFilmExist(filmId));
        delegateExecution.setVariable("freeOrNot", filmsService.isFilmFree(filmId));
        Films film = filmsService.findFilmById(filmId);
        delegateExecution.setVariable("cost", film.getCost());
    }
}
