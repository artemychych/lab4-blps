package com.example.lab4blps.delegators;

import com.example.lab4blps.models.Cards;
import com.example.lab4blps.models.Films;
import com.example.lab4blps.services.CardService;
import com.example.lab4blps.services.FilmsService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named("checkCard")
@RequiredArgsConstructor
public class CheckCard implements JavaDelegate {

    private final CardService cardService;
    private final FilmsService filmsService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Cards card = new Cards();
        String cardNumber = delegateExecution.getVariable("card_number").toString();
        card.setCardNumber(cardNumber);
        LocalDate localeDate = LocalDate.parse(delegateExecution.getVariable("card_date_end").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        card.setCardDateEnd(localeDate);
        Integer cvc = Integer.parseInt(delegateExecution.getVariable("card_cvc").toString());
        Integer money = Integer.parseInt(delegateExecution.getVariable("money").toString());
        String user_id = delegateExecution.getVariable("user_id").toString();
        card.setCardCVC(cvc);
        card.setMoney(money);
        card.setUserId(user_id);
        boolean result1 = cardService.checkCardInformation(cardNumber, localeDate, cvc, money, user_id);
        Integer filmId = Integer.parseInt(delegateExecution.getVariable("film_id").toString());
        Films film = filmsService.findFilmById(filmId);
        boolean result2 = result1 && (money >= film.getCost());

        delegateExecution.setVariable("checkedCard", result2);
    }
}
