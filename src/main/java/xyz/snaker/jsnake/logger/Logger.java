package xyz.snaker.jsnake.logger;

import java.util.Formatter;
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
     * Prints a message with the log level of information
     *
     * @param message The message to log
     **/
    <MSG> void info(MSG message);

    /**
     * Prints a message with the log level of debug
     *
     * @param message The message to log
     **/
    <MSG> void debug(MSG message);

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
     * <p>
     * Please note that the format placeholder is {@code '[]'} and not {@code '%'}
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void infof(String format, Object... args);

    /**
     * Prints a formatted message with a log level of information.
     * <p>
     * Please note that the format placeholder is {@code '[]'} and not {@code '%'}
     *
     * @param format The string to format
     * @param args   The arguments
     * @see Formatter
     **/
    void debugf(String format, Object... args);

    /**
     * Prints a formatted message with a log level of warning
     * <p>
     * Please note that the format placeholder is {@code '[]'} and not {@code '%'}
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void warnf(String format, Object... args);

    /**
     * Prints a formatted message with a log level of error
     * <p>
     * Please note that the format placeholder is {@code '[]'} and not {@code '%'}
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void errorf(String format, Object... args);

    /**
     * Prints a formatted message with a custom defined log level
     * <p>
     * Please note that the format placeholder is {@code '[]'} and not {@code '%'}
     *
     * @param format The string to format
     * @param args   The arguments
     * @see String#format(String, Object...)
     **/
    void printf(String format, LogLevel level, Object... args);

    /**
     * Starts a debug timer
     *
     * @param name The identifier for this timer
     **/
    void startTimer(String name);

    /**
     * Stops the current timer if one is running
     **/
    void stopTimer();

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
}
