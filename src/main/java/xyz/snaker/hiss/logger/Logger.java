package xyz.snaker.hiss.logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public interface Logger
{
    /**
     * A map of all the current loggers. Active or not
     **/
    Map<Class<?>, Logger> LOGGERS = new ConcurrentHashMap<>();

    /**
     * Checks if this logger is currently active
     *
     * @return True if this logger is currently operational
     **/
    boolean isActive();

    /**
     * Sets this logger's activity
     *
     * @param value The value to set
     **/
    void setActive(boolean value);

    /**
     * Prints a message with the log level of information
     *
     * @param message The message to log
     **/
    <MSG> void info(MSG message);

    /**
     * Prints a message with the log level of warning
     *
     * @param message The message to log
     **/
    <MSG> void warn(MSG message);

    /**
     * Prints a message with the log level of error
     *
     * @param message The message to log
     **/
    <MSG> void error(MSG message);

    /**
     * Prints a message with a custom defined log level
     *
     * @param message The message to log
     * @param level   The console log level
     **/
    <MSG> void print(MSG message, LogLevel level);

    /**
     * Prints a formatted message with a log level of information
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void infof(String format, Object... args);

    /**
     * Prints a formatted message with a log level of warning
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void warnf(String format, Object... args);

    /**
     * Prints a formatted message with a log level of error
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void errorf(String format, Object... args);

    /**
     * Prints a formatted message with a custom defined log level
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void printf(String format, LogLevel level, Object... args);
}
