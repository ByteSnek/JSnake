package xyz.snaker.jsnake.thread;

import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;

import java.util.function.Function;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class PeripheralException extends Thread
{
    private static final Logger LOGGER = Loggers.getLogger();
    private static final Function<String, String> ERROR_TEMPLATE = "Peripheral Exception Thrown: %s"::formatted;
    private static final String NO_ERROR_MESSAGE = ERROR_TEMPLATE.apply("No message specified");

    private final String message;
    private final UncaughtExceptionHandler handler;
    private final Exception cause;

    private PeripheralException(String message, Exception cause)
    {
        this.message = message;
        this.handler = (t, e) -> LOGGER.errorf(ERROR_TEMPLATE.apply(message));
        this.cause = cause;
    }

    private PeripheralException(Exception cause)
    {
        this(cause.getMessage(), cause);
    }

    private PeripheralException(String message)
    {
        this(message, new Exception());
    }

    private PeripheralException()
    {
        this(NO_ERROR_MESSAGE, new Exception());
    }

    public static void invoke(String message, Exception cause)
    {
        new PeripheralException(message, cause).start();
    }

    public static void invoke(Exception cause)
    {
        new PeripheralException(cause).start();
    }

    public static void invoke(String message)
    {
        new PeripheralException(message).start();
    }

    public static void invoke()
    {
        new PeripheralException().start();
    }

    @Override
    @SuppressWarnings({"ConstantValue", "CallToPrintStackTrace"})
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
