package xyz.snaker.jsnake.logger;

import java.util.List;
import java.util.Map;

/**
 * Created by SnakerBone on 2/12/2023
 **/
public class Loggers
{
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static Logger findLogger()
    {
        return findLogger(WALKER.getCallerClass());
    }

    public static Logger findLogger(Class<?> clazz)
    {
        return getLoggerMap().get(clazz);
    }

    public static Logger getLogger()
    {
        return getLogger(WALKER.getCallerClass(), true);
    }

    public static Logger getLogger(boolean defaultLoggerActive)
    {
        return getLogger(WALKER.getCallerClass(), defaultLoggerActive);
    }

    public static Logger getLogger(Class<?> clazz)
    {
        return getLogger(clazz, true);
    }

    public static Logger getLogger(Class<?> clazz, boolean defaultLoggerActive)
    {
        return getLoggerMap()
                .getOrDefault(clazz, newSimpleLogger(clazz, defaultLoggerActive));
    }

    public static List<Logger> getActiveLoggers()
    {
        return getLoggers().stream()
                .filter(Logger::isActive)
                .toList();
    }

    public static List<Logger> getLoggers()
    {
        return getLoggerMap().values()
                .stream()
                .toList();
    }

    public static Logger newSimpleLogger(Class<?> clazz, boolean active)
    {
        return new SimpleLogger(clazz, active);
    }

    public static Map<Class<?>, Logger> getLoggerMap()
    {
        return Logger.LOGGERS;
    }
}
