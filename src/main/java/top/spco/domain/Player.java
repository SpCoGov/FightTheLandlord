package top.spco.domain;

import top.spco.Main;

import java.util.List;

/**
 * @author SpCo
 * @date 2023/7/3 0003 21:17
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
            throw new RuntimeException("Illegal hand.");
        }
        this.hand = hand;
    }
}
