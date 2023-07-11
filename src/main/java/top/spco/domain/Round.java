package top.spco.domain;

import org.apache.logging.log4j.Logger;
import top.spco.Main;
import top.spco.utils.LogUtil;

import java.util.*;

/**
 * 这个类代表了斗地主游戏中的一轮对局。
 * 它包含了跟踪游戏当前状态的字段，例如当前的玩家是谁，行动顺序是怎样的，
 * 以及每个玩家的手牌，地主牌和地主玩家等等。
 * 它也包括了一些方法，用于初始化一轮游戏，决定玩家的行动顺序，和选择地主等等。
 *
 * @author SpCo
 * @version 1.1
 * @see Player
 * @see Cards
 * @since 1.0
 */
public class Round {
    private static final Logger LOGGER = LogUtil.getLogger();
    /**
     * 农民玩家
     */
    private List<Player> farmers;
    /**
     * 地主玩家
     */
    private Player landlord;
    /**
     * 地主牌
     */
    private List<Cards> landlordCards;
    /**
     * 玩家出牌顺序
     */
    private Map<Player, Integer> turnOrder;
    /**
     * 当前操作的玩家索引
     */
    private int currentPlayerIndex;
    /**
     * 本对局的全部玩家
     */
    private final List<Player> allPlayer = new ArrayList<>();

    /**
     * 初始化玩家的手牌。
     *
     * @param players 参与游戏的玩家列表
     */
    private void initCards(Player... players) {
        // 创建一副完整的牌
        List<Cards> deck = Main.cardService.createDeck();
        // 将牌打乱
        Collections.shuffle(deck);
        // 随机抽取3张牌作为地主牌
        landlordCards = deck.subList(0, 3);
        // 将牌从大到小排好
        landlordCards.sort(new Cards.CardComparator());
        // 取除地主牌之外的牌
        deck = deck.subList(3, deck.size());

        // 斗地主一共3人参与
        int numPlayers = 3;
        // 每人17张牌
        int cardsPerPlayer = 17;

        // 给每个玩家发牌
        for (int i = 0; i < numPlayers; i++) {
            List<Cards> playerHand = deck.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer);
            // 将牌从大到小排好
            playerHand.sort(new Cards.CardComparator());
            players[i].setHand(playerHand);
        }
    }

    /**
     * 确定玩家的行动顺序。
     *
     * @param playerA 玩家 A
     * @param playerB 玩家 B
     * @param playerC 玩家 C
     */
    private void determineTurnOrder(Player playerA, Player playerB, Player playerC) {
        // 使用 LinkedHashMap 保持插入顺序
        turnOrder = new LinkedHashMap<>();

        // 随机生成行动顺序
        List<Player> players = new ArrayList<>(Arrays.asList(playerA, playerB, playerC));
        Collections.shuffle(players);

        // 存储玩家行动顺序
        for (int i = 0; i < players.size(); i++) {
            turnOrder.put(players.get(i), i);
        }
    }

    /**
     * 获取下一个操作的玩家对象。
     *
     * @return 下一个操作的玩家对象
     */
    private Player getNextPlayer() {
        int i = (currentPlayerIndex + 1) % turnOrder.size();
        List<Player> players = new ArrayList<>(turnOrder.keySet());
        return players.get(i);
    }

    /**
     * 获取当前操作的玩家对象。
     *
     * @return 当前操作的玩家对象
     * @throws RuntimeException 如果无法找到当前操作的玩家对象
     */
    private Player getCurrentPlayer() {
        for (Map.Entry<Player, Integer> entry : turnOrder.entrySet()) {
            if (entry.getValue() == currentPlayerIndex) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Unable to find current player.");
    }

    /**
     * 将当前操作的玩家设置为下一个操作的玩家。
     *
     * @throws RuntimeException 如果无法找到当前操作的玩家对象或下一个操作的玩家对象
     */
    private void setNextPlayerAsCurrent() {
        Player currentPlayer = getCurrentPlayer();
        Player nextPlayer = getNextPlayer();
        if (currentPlayer == null || nextPlayer == null) {
            throw new RuntimeException("Unable to find current or next player.");
        }

        // 将下一个操作的玩家设为当前操作的玩家
        currentPlayerIndex = turnOrder.get(nextPlayer);
    }


    private void outputCards(Player playerA, Player playerB, Player playerC) {
        LOGGER.info("地主牌: " + landlordCards);
        LOGGER.info(playerA.getName() + ": " + playerA.getHand());
        LOGGER.info(playerB.getName() + ": " + playerB.getHand());
        LOGGER.info(playerC.getName() + ": " + playerC.getHand());
    }

    public Round(Player playerA, Player playerB, Player playerC) {
        LOGGER.info("开始初始化对局.");
        long startTime = System.nanoTime();
        allPlayer.add(0, playerA);
        allPlayer.add(1, playerB);
        allPlayer.add(2, playerC);
        // 洗牌, 获取地主牌和每个玩家的手牌
        initCards(playerA, playerB, playerC);
        // 确定玩家出牌顺序
        determineTurnOrder(playerA, playerB, playerC);

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        double milliseconds = (double) executionTime / 1_000_000;
        LOGGER.info("Done ({}ms)!", milliseconds);

        outputCards(playerA, playerB, playerC);
        for (Map.Entry<Player, Integer> entry : turnOrder.entrySet()) {
            Player player = entry.getKey();
            int order = entry.getValue();
            LOGGER.info(player.getName() + " -- " + order);
        }
        chooseLandlord();
        LOGGER.info("地主:" + landlord.getName() + "|农民:" + farmers.get(0).getName() + "&" + farmers.get(1).getName());
        while (getCurrentPlayer() != landlord) {
            setNextPlayerAsCurrent();
        }
        LOGGER.info("请" + getCurrentPlayer().getName() + "先出牌");
    }

    private void setLandlord(Player player) {
        landlord = player;
        farmers = new ArrayList<>(allPlayer);
        farmers.remove(landlord);
    }

    private void chooseLandlord() {
        // 初始化叫地主和抢地主次数
        int bidCount = 0;

        // 玩家叫地主对应的次数
        Map<Player, Integer> point = new HashMap<>();

        // 循环进行叫地主和抢地主的操作
        while (true) {
            int t = 0;
            bidCount++;
            LOGGER.info(getCurrentPlayer().getName() + ",请进行叫地主或抢地主的选择[y/n]: ");
            point.put(getCurrentPlayer(), 0);
            if (Main.commandSystem.getPlayerConfirmation()) {
                point.put(getCurrentPlayer(), 1);
                t++;
            }

            setNextPlayerAsCurrent();

            LOGGER.info(getCurrentPlayer().getName() + ",请进行叫地主或抢地主的选择[y/n]: ");
            point.put(getCurrentPlayer(), 0);
            if (Main.commandSystem.getPlayerConfirmation()) {
                point.put(getCurrentPlayer(), 1);
                t++;
            }

            setNextPlayerAsCurrent();

            LOGGER.info(getCurrentPlayer().getName() + ",请进行叫地主或抢地主的选择[y/n]: ");
            point.put(getCurrentPlayer(), 0);
            if (Main.commandSystem.getPlayerConfirmation()) {
                point.put(getCurrentPlayer(), 1);
                t++;
            }

            setNextPlayerAsCurrent();

            // 没人叫过地主
            if (point.get(allPlayer.get(0)) == 0 && point.get(allPlayer.get(1)) == 0 && point.get(allPlayer.get(2)) == 0) {
                LOGGER.info("三位玩家均未抢地主.开始重新发牌[{}/3]", bidCount);
                if (bidCount < 3) {
                    // 重新进行发牌流程
                    System.out.println("重新进行发牌流程,玩家出牌顺序不变!");
                    initCards(allPlayer.get(0), allPlayer.get(1), allPlayer.get(2));
                    outputCards(allPlayer.get(0), allPlayer.get(1), allPlayer.get(2));
                    for (Map.Entry<Player, Integer> entry : turnOrder.entrySet()) {
                        Player player = entry.getKey();
                        int order = entry.getValue();
                        LOGGER.info(player.getName() + " -- " + order);
                    }
                } else if (bidCount == 3) {
                    LOGGER.info("连续三次发牌都无人叫地主! 指定操作顺序第一位为地主.");
                    // 将第一次叫地主的玩家设为当前操作玩家
                    setLandlord(getCurrentPlayer());
                    break;
                }

                // 有人叫过地主
            } else {
                if (t != 0) {
                    if (t == 1) {
                        List<Player> playersWithValueOne = new ArrayList<>();

                        for (Map.Entry<Player, Integer> entry : point.entrySet()) {
                            if (entry.getValue() == 1) {
                                playersWithValueOne.add(entry.getKey());
                            }
                        }
                        if (playersWithValueOne.size() == 1) {
                            setLandlord(playersWithValueOne.get(0));
                        }
                    } else {
                        int i = 0;
                        for (Map.Entry<Player, Integer> entry : turnOrder.entrySet()) {
                            Player player = entry.getKey();
                            if (point.get(player) == 1) {
                                LOGGER.info(player.getName() + ",请进行叫地主或抢地主的选择[y/n]: ");
                                if (Main.commandSystem.getPlayerConfirmation()) {
                                    point.put(player, point.get(player) + 1);
                                    t++;
                                    if (i == 0) {
                                        break;
                                    }
                                }
                                i++;
                            }
                        }

                        // 点数最大的玩家
                        Player maxPlayer = null;
                        // 最大的点数
                        int maxValue = -999;

                        for (Map.Entry<Player, Integer> entry : point.entrySet()) {
                            // point数组中每个玩家和其对应的叫地主次数
                            Player player = entry.getKey();
                            int value = entry.getValue();

                            if (value > maxValue) {
                                // 如果有比最大点数还大的点数,责更新最大点数的玩家和最大点数
                                maxPlayer = player;
                                maxValue = value;
                            } else if (value == maxValue) {
                                // 如果值相等，则根据 turnOrder 中的值进行比较
                                int turnOrderValue = turnOrder.get(player);
                                int minTurnOrderValue = turnOrder.get(maxPlayer);
                                if (turnOrderValue < minTurnOrderValue) {
                                    maxPlayer = player;
                                }
                            }
                        }
                        assert maxPlayer != null;
                        setLandlord(maxPlayer);
                    }
                    break;
                }
            }
        }
    }
}
