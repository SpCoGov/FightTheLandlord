package top.spco.domain;

import java.util.Comparator;

/**
 * 这个枚举定义了斗地主游戏中的所有牌面。
 * <p>
 * 包括了四种花色（黑桃、红心、梅花、方块）的3至2（数字越大，代表的牌面越大），以及两个大小王。
 * 每个枚举值都有一个对应的等级（rank），用于比较牌面的大小。
 * <p>
 * 另外，还定义了一个内部类 CardComparator 用于比较两张牌的大小。
 *
 * @author SpCo
 * @version 1.1
 * @since 1.0
 */

public enum Cards {
    SPADE_3(3), SPADE_4(4), SPADE_5(5), SPADE_6(6), SPADE_7(7), SPADE_8(8), SPADE_9(9), SPADE_10(10),
    SPADE_J(11), SPADE_Q(12), SPADE_K(13), SPADE_A(14), SPADE_2(15),
    HEART_3(3), HEART_4(4), HEART_5(5), HEART_6(6), HEART_7(7), HEART_8(8), HEART_9(9), HEART_10(10),
    HEART_J(11), HEART_Q(12), HEART_K(13), HEART_A(14), HEART_2(15),
    CLUB_3(3), CLUB_4(4), CLUB_5(5), CLUB_6(6), CLUB_7(7), CLUB_8(8), CLUB_9(9), CLUB_10(10),
    CLUB_J(11), CLUB_Q(12), CLUB_K(13), CLUB_A(14), CLUB_2(15),
    DIAMOND_3(3), DIAMOND_4(4), DIAMOND_5(5), DIAMOND_6(6), DIAMOND_7(7), DIAMOND_8(8), DIAMOND_9(9), DIAMOND_10(10),
    DIAMOND_J(11), DIAMOND_Q(12), DIAMOND_K(13), DIAMOND_A(14), DIAMOND_2(15),
    BLACK_JOKER(16), RED_JOKER(17);

    private final int rank;

    Cards(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public static class CardComparator implements Comparator<Cards> {
        @Override
        public int compare(Cards card1, Cards card2) {
            // 降序排序
            int rankComparison = Integer.compare(card2.getRank(), card1.getRank());
            if (rankComparison != 0) {
                return rankComparison;
            } else {
                return card1.name().compareTo(card2.name());
            }
        }
    }
}
