package bytesnek.hiss.thread;

import bytesnek.hiss.logger.Logger;
import bytesnek.hiss.logger.Loggers;
import bytesnek.hiss.sneaky.Sneaky;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class UncaughtExceptionThread extends Thread
{
    private static final Logger LOGGER = Loggers.getLogger();

    private final UncaughtExceptionHandler handler;
    private final String message;
    private final boolean breakpoint;

    public UncaughtExceptionThread(Exception cause)
    {
        this.message = cause.getMessage();
        this.handler = (thread, throwable) -> LOGGER.errorf("[]: []", cause.getClass().getName(), message);
        this.breakpoint = false;
    }

    public UncaughtExceptionThread(Exception cause, boolean breakpoint)
    {
        this.message = cause.getMessage();
        this.handler = (thread, throwable) -> LOGGER.errorf("[]: []", cause.getClass().getName(), message);
        this.breakpoint = breakpoint;
    }

    public UncaughtExceptionThread(String message, Exception cause)
    {
        this.message = message;
        this.handler = (thread, throwable) -> LOGGER.errorf("[]: []", cause.getClass().getName(), message);
        this.breakpoint = false;
    }

    public UncaughtExceptionThread(String message, Exception cause, boolean breakpoint)
    {
        this.message = message;
        this.handler = (thread, throwable) -> LOGGER.errorf("[]: []", cause.getClass().getName(), message);
        this.breakpoint = breakpoint;
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
    @SuppressWarnings("ConstantValue")
    public void run()
    {
        if (true) {
            throw new RuntimeException(message);
        }
    }

    @Override
    public synchronized void start()
    {
        Sneaky.createBreakpoint(breakpoint);

        super.start();
    }

    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh)
    {
        super.setUncaughtExceptionHandler(handler);
    }
}
