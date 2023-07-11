package top.spco.service.impl;

import org.apache.logging.log4j.Logger;
import top.spco.Main;
import top.spco.service.CommandService;
import top.spco.utils.LogUtil;

/**
 * @author SpCo
 * @date 2023/7/7 0007 17:37
 */
public class CommandServiceImpl implements CommandService {
    private static final Logger LOGGER = LogUtil.getLogger();


    @Override
    public void onCommand(String command, String label, String[] args) {
        if (Main.commandSystem.isWaitingForConfirmation) {
            Main.commandSystem.handleConfirmation(command);
            return;
        }
        switch (label) {
            case "test" -> {
                LOGGER.info("一条测试参数. 这条参数包含了{}个参数, 下面开始向控制台输出每个参数.", args.length);
                for (int i = 0; i < args.length; i++) {
                    LOGGER.info("第{}个参数: {}", i + 1, args[i]);
                }
            }
            case "stop" -> Main.commandSystem.setInterrupt();
            default -> LOGGER.info("未知或不完整的命令.");
        }
    }
}
