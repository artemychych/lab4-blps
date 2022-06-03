package com.example.lab4blps.delegators;

import com.example.lab4blps.models.Cards;
import com.example.lab4blps.services.CardService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named("addCardToUser")
@RequiredArgsConstructor
public class AddCardToUser implements JavaDelegate {
    private final IdentityService identityService;
    private final CardService cardService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String cardNumber = delegateExecution.getVariable("card_number").toString();
        LocalDate localeDate = LocalDate.parse(delegateExecution.getVariable("card_date_end").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Integer cvc = Integer.parseInt(delegateExecution.getVariable("card_cvc").toString());
        Integer money = Integer.parseInt(delegateExecution.getVariable("money").toString());
        String user_id = delegateExecution.getVariable("user_id").toString();

        cardService.addCard(cardNumber, localeDate, cvc, money, user_id);

    }
}
