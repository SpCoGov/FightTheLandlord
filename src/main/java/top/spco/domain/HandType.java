package top.spco.domain;

/**
 * HandType 枚举定义了所有可能的牌型，包括其对应的中文翻译。这些牌型用于标识和分类玩家手中的牌的类型。
 * 每种牌型都与一个字符串相关联，该字符串是牌型的中文翻译。
 *
 * @author SpCo
 * @version 1.0
 * @since 2023/7/11
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
