package top.spco.service;

import top.spco.domain.Cards;

import java.util.List;

/**
 * @author SpCo
 * @date 2023/7/4 0004 0:39
 */
public interface CardService {
    boolean isHandValid(List<Cards> hand);

    List<Cards> createDeck();
}
