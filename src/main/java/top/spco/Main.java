package top.spco;

import org.apache.logging.log4j.Logger;
import top.spco.domain.Player;
import top.spco.domain.Round;
import top.spco.service.CardService;
import top.spco.service.HandTypeService;
import top.spco.service.impl.CardServiceImpl;
import top.spco.service.impl.HandTypeServiceImpl;
import top.spco.system.CommandSystem;
import top.spco.utils.LogUtil;

/**
 * @author SpCo
 * @date 2023/7/3 0003 19:14
 */

public class Main {
    public static final CommandSystem commandSystem = CommandSystem.getInstance();
    private static final Logger LOGGER = LogUtil.getLogger();
    public static CardService cardService = new CardServiceImpl();
    public static HandTypeService handTypeService = new HandTypeServiceImpl();

    public static void main(String[] args) {
        Player a = new Player("A");
        Player b = new Player("B");
        Player c = new Player("C");
        Round round = new Round(a, b, c);

    }
}