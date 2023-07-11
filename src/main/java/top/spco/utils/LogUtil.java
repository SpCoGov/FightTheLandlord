package top.spco.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.spi.LoggerContext;

import java.util.function.Supplier;

/**
 * @author SpCo
 * @date 2023/7/7 0007 22:00
 */
public class LogUtil {
    public static final String FATAL_MARKER_ID = "FATAL";
    public static final Marker FATAL_MARKER = MarkerManager.getMarker(FATAL_MARKER_ID);
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static boolean isLoggerActive() {
        final LoggerContext loggerContext = LogManager.getContext();
        if (loggerContext instanceof LifeCycle lifeCycle) {
            return !lifeCycle.isStopped();
        }
        // 合理的默认值? 在最坏的情况下, 即使没有日志记录, 损失也不会很大.
        return true;
    }

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
     * <b>该函数或方法的行为可能会因调用者的不同而改变, 因此不应该尝试将其包装在其他函数或方法中.</b>
     */
    public static Logger getLogger() {
        return LogManager.getLogger(STACK_WALKER.getCallerClass());
    }
}
