package top.spco.domain;

import top.spco.Main;
import top.spco.exceptions.InvalidHandException;

import java.util.List;

/**
 * 这个类表示斗地主游戏中的一名玩家。
 * <p>
 * 每名玩家都有一个名字，以及一手牌。玩家的牌面可以通过 setHand 方法设置，此方法会检查给定的手牌是否合法。
 * 不合法的手牌将抛出 InvalidHandException。
 *
 * @author SpCo
 * @version 1.1
 * @since 1.0
 */

public class Player {
    private final String name;
    private List<Cards> hand;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Cards> getHand() {
        return hand;
    }

    public void setHand(List<Cards> hand) {
        if (!Main.cardService.isHandValid(hand)) {
            throw new InvalidHandException("Illegal hand");
        }
        this.hand = hand;
    }
}
