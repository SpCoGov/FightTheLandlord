package top.spco.service;

import top.spco.domain.Cards;
import top.spco.domain.HandType;

import java.util.List;

/**
 * HandTypeService 接口定义了一系列方法用于确定牌型。
 *
 * @author SpCo
 * @version 1.1
 * @since 1.0
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
