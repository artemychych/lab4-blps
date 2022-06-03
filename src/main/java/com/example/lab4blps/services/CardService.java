package com.example.lab4blps.services;


import com.example.lab4blps.exception.ResourceNotFoundException;
import com.example.lab4blps.models.Cards;
import com.example.lab4blps.models.Films;
import com.example.lab4blps.repositories.CardsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    @Autowired
    private CardsRepo cardsRepo;


    public int findMaxMoney(String userId) {
        List<Cards> cards = cardsRepo.findCardsByUserId(userId);

        int minn = Integer.MAX_VALUE;
        for (Cards i : cards) {
            if (i.getMoney() < minn) {
                minn = i.getMoney();
            }
        }
        if (minn == Integer.MAX_VALUE) {
            return 0;
        }
        return minn;

    }

    public void modifyCardMoneyIfExist(String userId, Films film) {
        List<Cards> cards = cardsRepo.findCardsByUserId(userId);

        if (cards.size() == 0) {
            throw new ResourceNotFoundException("error no card found");
        } else {
            boolean flag = false;
            for (Cards i : cards) {
                if (i.getMoney() > film.getCost()) {
                    i.setMoney(i.getMoney() - film.getCost());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new ResourceNotFoundException("error no money found");
            }
        }
    }

    public void addCard(String cardNumber, LocalDate cardDateEnd, Integer cardCVC, Integer money, String userId) {

        if (!checkCardInformation(cardNumber, cardDateEnd, cardCVC, money, userId)) {
            throw new ResourceNotFoundException("Bad data in cardDto");
        }

        if (cardsRepo.countCardsByCardNumber(cardNumber) != 0) {
            throw new ResourceNotFoundException("this card already exist");
        }

        Cards newCard = new Cards();
        newCard.setCardNumber(cardNumber);
        newCard.setCardCVC(cardCVC);
        newCard.setMoney(money);
        newCard.setCardDateEnd(cardDateEnd);
        newCard.setUserId(userId);
        cardsRepo.save(newCard);
    }

    public boolean checkCardInformation(String cardNumber, LocalDate cardDateEnd, Integer cardCVC, Integer money, String userId) {
        String regex_cardNumber = "(^$|[0-9]{16})";
        String regex_cardCVC = "(^$|[0-9]{3})";

        if (!cardNumber.matches(regex_cardNumber)) {
            return false;
        }
        if (!cardCVC.toString().matches(regex_cardCVC)) {
            return false;
        }
        if (money < 0) {
            return false;
        }

        if (cardDateEnd.compareTo(LocalDate.now()) < 0) {
            return false;
        }
        return true;
    }



}
