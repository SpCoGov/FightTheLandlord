package top.spco.system;

import org.apache.logging.log4j.Logger;
import top.spco.service.CommandService;
import top.spco.service.impl.CommandServiceImpl;
import top.spco.utils.LogUtil;

import java.util.Scanner;

/**
 * @author SpCo
 * @date 2023/7/7 0007 22:35
 */
public class CommandSystem {
    private static final Logger LOGGER = LogUtil.getLogger();
    private Thread commandThread;
    private Scanner scanner;
    public static final CommandService commandService = new CommandServiceImpl();
    private volatile static CommandSystem instance;
    public boolean isWaitingForConfirmation = false;

    private boolean confirmationResult;

    private CommandSystem() {
        init();
    }

    public static CommandSystem getInstance() {
        if (instance == null) {
            instance = new CommandSystem();
        }
        return instance;
    }

    private void init() {
        commandThread = new Thread(() -> {
            scanner = new Scanner(System.in);
            while (true) {
                if (commandThread.isInterrupted()) {
                    // 清理资源，关闭scanner
                    scanner.close();
                    // 中断循环
                    break;
                }
                // 读取用户输入
                String userInput = scanner.nextLine();
                // 将用户的输入以 空格 为分隔符分割
                String[] parts = userInput.split(" ");
                // 检查parts数组是否为空
                if (parts.length > 0) {
                    // 创建一个长度为parts数组的长度减一的数组, 用于存储命令的参数
                    String[] otherParts = new String[parts.length - 1];
                    // 将parts数组中从第二个元素开始的所有元素复制到新的数组中
                    System.arraycopy(parts, 1, otherParts, 0, parts.length - 1);
                    // parts[0]为命令名, otherParts数组为命令的参数
                    commandService.onCommand(userInput, parts[0], otherParts);
                }
            }
        }, "Command Thread");
        commandThread.start();
    }

    public void setInterrupt() {
        commandThread.interrupt();
    }

    /**
     * 获取用户的确认输入.
     * <p>
     * 当调用该方法时, 当前线程将进入等待状态, 直到用户输入了确认信息. 用户可以输入 "y" 或 "n" 来进行确认.
     * <p>
     * 该方法将阻塞线程, 并返回用户的确认结果.
     *
     * @return 用户的确认结果，如果用户输入为 "y" 则返回 true，如果用户输入为 "n" 则返回 false
     * @throws RuntimeException 如果在等待期间发生了中断异常
     */
    public synchronized boolean getPlayerConfirmation() {
        isWaitingForConfirmation = true;
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("CommandSystem getPlayerConfirmation was interrupted", e);
        }
        return confirmationResult;
    }

    /**
     * 处理用户的确认输入.
     * 注意: 此方法仅供内部使用, <b>不应该</b>被外部调用.
     * <p>
     * 如果需要用户的确认输入, 请使用 {@link #getPlayerConfirmation()} 方法.
     *
     * @param userInput 用户的输入字符串
     */
    public synchronized void handleConfirmation(String userInput) {
        if ("y".equalsIgnoreCase(userInput)) {
            confirmationResult = true;
        } else if ("n".equalsIgnoreCase(userInput)) {
            confirmationResult = false;
        } else {
            System.out.println("输入错误，请重新输入.");
            return;
        }
        isWaitingForConfirmation = false;
        notifyAll();
    }

}