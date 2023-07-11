package top.spco.domain;

import java.util.List;

/**
 * @author SpCo
 * @date 2023/7/11 0011 17:22
 */
public enum HandType {
    SINGLE("单牌"),
    PAIR("对子"),
    THREE_OF_A_KIND("三张"),
    STRAIGHT("顺子"),
    STRAIGHT_PAIR("连对"),
    THREE_WITH_ONE("三带一"),
    THREE_WITH_PAIR("三带一对"),
    FOUR_WITH_TWO("四带二"),
    FOUR_WITH_PAIR("四带两对"),
    AIRPLANE("飞机"),
    AIRPLANE_WITH_WINGS("飞机"),
    BOMB("炸弹"),
    ROCKET("王炸"),
    ILLEGAL("不合法的牌组");

    private final String translation;
    HandType(String translation) {
        this.translation = translation;
    }

    /**
     * 获取牌组类型的翻译。
     *
     * @return 牌组类型的翻译
     */
    public String getTranslation() {
        return translation;
    }
}
