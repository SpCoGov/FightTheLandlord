package top.spco.service.impl;

import top.spco.domain.Cards;
import top.spco.service.CardService;

import java.util.*;

/**
 * @author SpCo
 * @date 2023/7/4 0004 0:44
 */
public class CardServiceImpl implements CardService {
    @Override
    public boolean isHandValid(List<Cards> hand) {
        Set<Cards> cardSet = new HashSet<>(hand);
        return cardSet.size() == hand.size();
    }

    @Override
    public List<Cards> createDeck() {
        return new ArrayList<>(Arrays.asList(Cards.values()));
    }


}
