package top.spco.exceptions;

/**
 * 当手牌不合法时,抛出此异常.
 * 手牌可能因为多种原因而不合法.例如包含不存在的牌,重复的牌或者违反了其他由牌类游戏定义的规则.
 *
 * @author SpCo
 * @version 1.1
 * @since 1.1
 */
public class InvalidHandException extends RuntimeException {

    /**
     * 使用详细消息构造一个 InvalidHandException。
     *
     * @param message 包含详细消息的字符串
     */
    public InvalidHandException(String message) {
        super(message);
    }

    /**
     * 使用详细消息和导致此异常的原因构造一个 InvalidHandException。
     *
     * @param message 包含详细消息的字符串
     * @param cause 导致此异常的 Throwable
     */
    public InvalidHandException(String message, Throwable cause) {
        super(message, cause);
    }
}
