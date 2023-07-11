package top.spco.service;

/**
 * CommandService 接口定义了对命令的处理方法。
 *
 * @author SpCo
 * @version 1.1
 * @since 1.0
 */
public interface CommandService {
    void onCommand(String command, String label, String[] args);
}
