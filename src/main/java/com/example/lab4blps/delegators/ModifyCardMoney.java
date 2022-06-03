package com.example.lab4blps.delegators;

import com.example.lab4blps.models.Films;
import com.example.lab4blps.services.CardService;
import com.example.lab4blps.services.FilmsService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("modifyCardMoney")
@RequiredArgsConstructor
public class ModifyCardMoney implements JavaDelegate {
    private final CardService cardService;
    private final FilmsService filmsService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String user_id = delegateExecution.getVariable("user_id").toString();
        Integer filmId = Integer.parseInt(delegateExecution.getVariable("film_id").toString());
        Films film = filmsService.findFilmById(filmId);
        cardService.modifyCardMoneyIfExist(user_id, film);
    }
}
