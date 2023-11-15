package bytesnek.hiss.thread;

import bytesnek.hiss.logger.Logger;
import bytesnek.hiss.logger.SimpleLogger;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class UncaughtExceptionThread extends Thread
{
    private static final Logger LOGGER = new SimpleLogger(UncaughtExceptionThread.class, true);

    private final UncaughtExceptionHandler handler;
    private final String message;

    public UncaughtExceptionThread(Exception cause)
    {
        this.message = cause.getMessage();
        this.handler = (thread, throwable) -> LOGGER.errorf("[]: []", cause.getClass().getName(), message);
    }

    public UncaughtExceptionThread(String message, Exception cause)
    {
        this.message = message;
        this.handler = (thread, throwable) -> LOGGER.errorf("[]: []", cause.getClass().getName(), message);
    }

    public static void createAndRun(Exception cause)
    {
        UncaughtExceptionThread thread = new UncaughtExceptionThread(cause);
        thread.start();
    }

    public static void createAndRun(String message, Exception cause)
    {
        UncaughtExceptionThread thread = new UncaughtExceptionThread(message, cause);
        thread.start();
    }

    @Override
    public void run()
    {
        throw new RuntimeException(message);
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
    {
        super.setUncaughtExceptionHandler(handler);
    }
}
