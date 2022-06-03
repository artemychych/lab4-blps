package com.example.lab4blps.delegators;

import com.example.lab4blps.services.CardService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.IdentityService;
import javax.inject.Named;

@Named("checkIfUserHasMoney")
@RequiredArgsConstructor
public class CheckIfUserHasMoney implements JavaDelegate {
    private final CardService cardService;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer cost = Integer.parseInt(delegateExecution.getVariable("cost").toString());
        String username = identityService.getCurrentAuthentication().getUserId();
        delegateExecution.setVariable("user_id", username);
        int maxMoney = cardService.findMaxMoney(username);
        boolean haveEnoughMoney = maxMoney >= cost;
        delegateExecution.setVariable("haveEnoughMoney", haveEnoughMoney);
    }
}
