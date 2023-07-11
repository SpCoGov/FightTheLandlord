package top.spco.service;

import top.spco.domain.Cards;
import top.spco.domain.HandType;

import java.util.List;

/**
 * @author SpCo
 * @date 2023/7/11 0011 17:26
 */
public interface HandTypeService {
    HandType getHandType(List<Cards> cards);

    boolean isSingle(List<Cards> cards);

    boolean isPair(List<Cards> cards);

    boolean isThreeOfAKind(List<Cards> cards);

    boolean isStraight(List<Cards> cards);

    boolean isStraightPair(List<Cards> cards);

    boolean isThreeWithOne(List<Cards> cards);

    boolean isThreeWithPair(List<Cards> cards);

    boolean isFourWithTwo(List<Cards> cards);

    boolean isFourWithPair(List<Cards> cards);

    boolean isAirplane(List<Cards> cards);
    boolean isAirplaneWithWings(List<Cards> cards);

    boolean isBomb(List<Cards> cards);

    boolean isRocket(List<Cards> cards);

}
