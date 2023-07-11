package top.spco.service;

/**
 * @author SpCo
 * @date 2023/7/7 0007 17:37
 */
public interface CommandService {
    void onCommand(String command, String label, String[] args);
}
