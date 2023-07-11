package top.spco.service.impl;

import top.spco.Main;
import top.spco.domain.Cards;
import top.spco.domain.HandType;
import top.spco.service.HandTypeService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SpCo
 * @date 2023/7/11 0011 17:26
 */
public class HandTypeServiceImpl implements HandTypeService {
    @Override
    public HandType getHandType(List<Cards> cards) {
        // 对牌组进行排序, 以便后续判断
        cards.sort(new Cards.CardComparator());
        if (isSingle(cards)) {
            return HandType.SINGLE;
        } else if (isPair(cards)) {
            return HandType.PAIR;
        } else if (isThreeOfAKind(cards)) {
            return HandType.THREE_OF_A_KIND;
        } else if (isThreeWithOne(cards)) {
            return HandType.THREE_WITH_ONE;
        } else if (isThreeWithPair(cards)) {
            return HandType.THREE_WITH_PAIR;
        } else if (isStraight(cards)) {
            return HandType.STRAIGHT;
        } else if (isStraightPair(cards)) {
            return HandType.STRAIGHT_PAIR;
        } else if (isAirplane(cards)) {
            return HandType.AIRPLANE;
        } else if (isAirplaneWithWings(cards)) {
            return HandType.AIRPLANE_WITH_WINGS;
        } else if (isFourWithTwo(cards)) {
            return HandType.FOUR_WITH_TWO;
        } else if (isFourWithPair(cards)) {
            return HandType.FOUR_WITH_PAIR;
        } else if (isRocket(cards)) {
            return HandType.ROCKET;
        } else if (isBomb(cards)) {
            return HandType.BOMB;
        } else {
            return HandType.ILLEGAL;
        }
    }

    /**
     * 判断一手牌是否为 "单牌" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "单牌" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isSingle(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 出的牌只有一张
        return cards.size() == 1;
    }

    /**
     * 判断一手牌是否为 "对子" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "对子" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isPair(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 出的牌有两张且这两张牌大小一样
        return cards.size() == 2 && (cards.get(0).getRank() == cards.get(1).getRank());
    }

    /**
     * 判断一手牌是否为 "三张" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "三张" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isThreeOfAKind(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 出的牌有三张且这三张牌大小一样
        return cards.size() == 3 && cards.get(0).getRank() == cards.get(1).getRank() && cards.get(0).getRank() == cards.get(2).getRank();
    }

    /**
     * 判断一手牌是否为 "顺子" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "顺子" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isStraight(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 不包括2和王
        for (Cards card : cards) {
            if (card.getRank() >= Cards.SPADE_2.getRank()) {
                return false;
            }
        }
        // 牌组必须至少包含5张牌才能构成顺子
        if (cards.size() < 5) {
            return false;
        }
        // 判断牌组是否为连续的顺子
        int prevRank = cards.get(0).getRank();
        for (int i = 1; i < cards.size(); i++) {
            int currentRank = cards.get(i).getRank();
            if (currentRank != prevRank - 1) {
                return false;
            }
            prevRank = currentRank;
        }
        return true;
    }

    /**
     * 判断一手牌是否为 "连对" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "连对" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isStraightPair(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 不包括2和王
        for (Cards card : cards) {
            if (card.getRank() >= Cards.SPADE_2.getRank()) {
                return false;
            }
        }
        // 首先，对牌进行排序，并按照rank进行分组
        Map<Integer, List<Cards>> groups = cards.stream()
                .sorted(Comparator.comparing(Cards::getRank).reversed())
                .collect(Collectors.groupingBy(Cards::getRank, LinkedHashMap::new, Collectors.toList()));

        // 检查每组是否都是对子
        for (List<Cards> group : groups.values()) {
            if (group.size() != 2) {
                return false;
            }
        }

        // 然后，检查分组的顺序是否连续
        Integer prevRank = null;
        for (Integer rank : groups.keySet()) {
            if (prevRank != null && rank != prevRank - 1) {
                return false;
            }
            prevRank = rank;
        }

        return true;
    }

    /**
     * 判断一手牌是否为 "三带一" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "三带一" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isThreeWithOne(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 牌组必须包含4张牌才能构成三带一
        if (cards.size() != 4) {
            return false;
        }

        // 判断牌组是否为三带一
        int rank1 = cards.get(0).getRank();
        int rank2 = cards.get(1).getRank();
        int rank3 = cards.get(2).getRank();
        int rank4 = cards.get(3).getRank();

        // 有两种情况满足三带一的条件：(1) AAAx (2) xAAA
        return (rank1 == rank2 && rank2 == rank3 && rank3 != rank4)
                || (rank1 != rank2 && rank2 == rank3 && rank3 == rank4);
    }

    /**
     * 判断一手牌是否为 "三带一对" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "三带一对" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isThreeWithPair(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 如果牌的数量不是5, 直接返回 false
        if (cards.size() != 5) {
            return false;
        }

        // 创建一个 HashMap 来存储每个点数的牌出现的次数
        Map<Integer, Integer> rankCounts = new HashMap<>();

        // 遍历所有的牌，对每个点数的牌的数量进行计数
        for (Cards card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        // 检查是否存在三张相同点数的牌
        boolean hasThreeOfAKind = rankCounts.containsValue(3);
        // 检查是否存在两张相同点数的牌
        boolean hasPair = rankCounts.containsValue(2);

        // 如果存在一组三张相同点数的牌和一组两张相同点数的牌，那么这手牌就是"三带一对"牌型
        return hasThreeOfAKind && hasPair;
    }

    /**
     * 判断一手牌是否为 "四带二" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "四带二" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isFourWithTwo(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 如果牌的数量不等于6, 直接返回 false
        if (cards.size() != 6) {
            return false;
        }

        // 使用 HashMap 记录每个点数的牌出现的次数
        Map<Integer, Integer> rankCounts = new HashMap<>();
        for (Cards card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        // 检查是否存在四张相同点数的牌
        return rankCounts.containsValue(4);
    }

    /**
     * 判断一手牌是否为 "四带两对" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "四带两对" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isFourWithPair(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 如果牌的数量不等于8, 直接返回 false
        if (cards.size() != 8) {
            return false;
        }

        // 使用 HashMap 记录每个点数的牌出现的次数
        Map<Integer, Integer> rankCounts = new HashMap<>();
        for (Cards card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        // 检查是否存在四张相同点数的牌, 以及两对
        int fourCount = 0;
        int pairCount = 0;
        for (int count : rankCounts.values()) {
            if (count == 4) {
                fourCount++;
            } else if (count == 2) {
                pairCount++;
            }
        }

        return fourCount == 1 && pairCount == 2;
    }

    /**
     * 判断一手牌是否为 "飞机" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "飞机" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isAirplane(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 如果牌的数量不是3的倍数, 直接返回 false
        if (cards.size() % 3 != 0) {
            return false;
        }

        // 使用 TreeMap 记录每个点数的牌出现的次数，TreeMap可以保持key的有序性
        TreeMap<Integer, Integer> rankCounts = new TreeMap<>();
        for (Cards card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        // 检查所有点数是否连续，且每个点数都有三张牌
        int prevRank = -1;
        for (Map.Entry<Integer, Integer> entry : rankCounts.entrySet()) {
            if (entry.getKey() == Cards.SPADE_2.getRank() && entry.getValue() >= 3) {
                return false;
            }
            if (entry.getValue() != 3 || (prevRank != -1 && entry.getKey() != prevRank + 1)) {
                return false;
            }
            prevRank = entry.getKey();
        }

        return true;
    }

    /**
     * 判断一手牌是否为 "飞机(带翅膀)" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "飞机(带翅膀)" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isAirplaneWithWings(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 使用 TreeMap 记录每个点数的牌出现的次数，TreeMap可以保持key的有序性
        TreeMap<Integer, Integer> rankCounts = new TreeMap<>();
        for (Cards card : cards) {
            rankCounts.put(card.getRank(), rankCounts.getOrDefault(card.getRank(), 0) + 1);
        }

        // 计算三条的数量和翅膀的数量
        int threeCount = 0;
        int wingCount = 0;
        for (int count : rankCounts.values()) {
            if (count >= 3) {
                threeCount++;
            } else {
                wingCount += count;
            }
        }

        // "三条" 的数量必须等于 "翅膀" 的数量，且至少需要有两个"三条"
        if (threeCount != wingCount || threeCount < 2) {
            return false;
        }

        // 检查所有 "三条" 的点数是否连续
        int prevRank = -1;
        for (Map.Entry<Integer, Integer> entry : rankCounts.entrySet()) {
            if (entry.getKey() == Cards.SPADE_2.getRank() && entry.getValue() >= 3) {
                return false;
            }
            if (entry.getValue() >= 3 && (prevRank != -1 && entry.getKey() != prevRank + 1)) {
                return false;
            }
            if (entry.getValue() >= 3) {
                prevRank = entry.getKey();
            }
        }

        return true;
    }

    /**
     * 判断一手牌是否为 "炸弹" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "炸弹" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isBomb(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 出的牌有四张且这四张牌大小一样
        return cards.size() == 4 && cards.get(0).getRank() == cards.get(1).getRank() && cards.get(0).getRank() == cards.get(2).getRank() && cards.get(0).getRank() == cards.get(3).getRank();
    }

    /**
     * 判断一手牌是否为 "王炸" 牌型
     *
     * @param cards 一手牌的列表，列表中的卡牌已从大到小排序
     * @return 如果是 "王炸" 牌型，返回 true；否则返回 false
     */
    @Override
    public boolean isRocket(List<Cards> cards) {
        Main.cardService.isHandValid(cards);
        // 出的牌有两张且第一张牌是大王第二张牌是小王
        return cards.size() == 2 && cards.get(0) == Cards.RED_JOKER && cards.get(1) == Cards.BLACK_JOKER;
    }
}