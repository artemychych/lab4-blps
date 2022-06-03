package com.example.lab4blps.delegators;

import com.example.lab4blps.services.UserFilmService;
import jdk.jfr.Name;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("addUserFilm")
@RequiredArgsConstructor
public class AddUserFilm implements JavaDelegate {
    private final IdentityService identityService;
    private final UserFilmService userFilmService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = identityService.getCurrentAuthentication().getUserId();
        Integer filmId = Integer.parseInt(delegateExecution.getVariable("film_id").toString());
        userFilmService.addFilmToUser(username, filmId);
    }
}
