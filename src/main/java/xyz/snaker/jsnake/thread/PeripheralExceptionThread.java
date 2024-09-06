package xyz.snaker.jsnake.thread;

import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;

import java.util.function.Function;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class PeripheralExceptionThread extends Thread
{
    private static final Logger LOGGER = Loggers.getLogger();
    private static final Function<String, String> ERROR_TEMPLATE = "Exception Thrown: %s"::formatted;
    private static final String NO_ERROR_MESSAGE = ERROR_TEMPLATE.apply("No message specified");

    private final String message;
    private final UncaughtExceptionHandler handler;
    private final Exception cause;

    public PeripheralExceptionThread(String message, Exception cause)
    {
        this.message = message;
        this.handler = (thread, throwable) -> LOGGER.errorf(ERROR_TEMPLATE.apply(message));
        this.cause = cause;
    }

    public PeripheralExceptionThread(Exception cause)
    {
        this(cause.getMessage(), cause);
    }

    public PeripheralExceptionThread(String message)
    {
        this(message, new Exception());
    }

    public PeripheralExceptionThread()
    {
        this(NO_ERROR_MESSAGE);
    }

    public static PeripheralExceptionThread invoke(String message, Exception cause)
    {
        PeripheralExceptionThread thread = new PeripheralExceptionThread(message, cause);
        thread.start();
        return thread;
    }

    public static PeripheralExceptionThread invoke(Exception cause)
    {
        return invoke(cause.getMessage(), cause);
    }

    public static PeripheralExceptionThread invoke(String message)
    {
        return invoke(message, new Exception());
    }

    public static PeripheralExceptionThread invoke()
    {
        return invoke(NO_ERROR_MESSAGE);
    }

    @Override
    @SuppressWarnings("all")
    public void run()
    {
        cause.printStackTrace();

        if (true) {
            throw new RuntimeException(message);
        }
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
    {
        super.setUncaughtExceptionHandler(handler);
    }
}
