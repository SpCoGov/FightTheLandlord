package top.spco.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.spi.LoggerContext;

import java.util.function.Supplier;

/**
 * LogUtil提供了日志工具方法，以便于进行日志记录。它提供了获取Logger的方法，检查Logger活跃状态的方法，
 * 并提供了用于懒加载计算结果的工具方法。
 *
 * @author SpCo
 * @since 1.0
 * @version 1.1
 */
public class LogUtil {
    public static final String FATAL_MARKER_ID = "FATAL";
    public static final Marker FATAL_MARKER = MarkerManager.getMarker(FATAL_MARKER_ID);
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * 检查当前Logger的活跃状态。如果Logger的上下文是一个生命周期(LifeCycle)实例，并且该实例已经停止，
     * 那么返回false。否则，返回true。
     *
     * @return Logger的活跃状态。如果Logger已经停止，则返回false，否则返回true。
     */
    public static boolean isLoggerActive() {
        final LoggerContext loggerContext = LogManager.getContext();
        if (loggerContext instanceof LifeCycle lifeCycle) {
            return !lifeCycle.isStopped();
        }
        // 合理的默认值? 在最坏的情况下, 即使没有日志记录, 损失也不会很大.
        return true;
    }

    /**
     * 返回一个对象，该对象会在调用其toString方法时，调用并返回所提供的结果供应商的结果的字符串表示形式。
     * 这可以用于懒加载计算结果。
     *
     * @param result 一个结果供应商，当需要字符串表示形式时，将会被调用。
     * @return 一个对象，其toString方法会返回结果供应商的结果的字符串表示形式。
     */
    public static Object defer(final Supplier<Object> result) {
        class ToString {
            @Override
            public String toString() {
                return result.get().toString();
            }
        }

        return new ToString();
    }

    /**
     * Caller sensitive, DO NOT WRAP
     * <p>
     * <b>该函数或方法的行为可能会因调用者的不同而改变，因此不应该尝试将其包装在其他函数或方法中。</b>
     *
     * @return 一个与调用者类关联的Logger。
     */
    public static Logger getLogger() {
        return LogManager.getLogger(STACK_WALKER.getCallerClass());
    }
}
