package top.spco.service.impl;

import top.spco.domain.Cards;
import top.spco.exceptions.InvalidHandException;
import top.spco.service.CardService;

import java.util.*;

/**
 * 这是一个 CardService 接口的实现类。
 *
 * @author SpCo
 * @version 1.1
 * @since 1.0
 */
public class CardServiceImpl implements CardService {
    @Override
    public boolean isHandValid(List<Cards> hand) {
        Set<Cards> cardSet = new HashSet<>(hand);
        if (cardSet.size() != hand.size()) throw new InvalidHandException("Illegal hand.");
        return true;
    }

    @Override
    public List<Cards> createDeck() {
        return new ArrayList<>(Arrays.asList(Cards.values()));
    }


}
