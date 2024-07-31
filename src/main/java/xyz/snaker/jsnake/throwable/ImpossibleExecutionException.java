package xyz.snaker.jsnake.throwable;

/**
 * Throws when code that is deemed unreachable executes. Example:
 * <pre> {@code
 *    static final int alwaysOne = 1; // Is always 1. It will (should) never change
 *
 *    // Example
 *    if (alwaysOne == 2) {
 *        throw new ImpossibleExecutionException("How did this execute...");
 *    }
 *
 *    // Another (impossible) example
 *    if (true) {
 *        return;
 *        throw new ImpossibleExecutionException("Unreachable code reached");
 *    }
 * }</pre>
 * <p>
 * Created by SnakerBone on 10/13/2023
 **/
public class ImpossibleExecutionException extends RuntimeException
{
    public ImpossibleExecutionException()
    {
        super();
    }

    public ImpossibleExecutionException(String message)
    {
        super(message);
    }

    public ImpossibleExecutionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ImpossibleExecutionException(Throwable cause)
    {
        super(cause);
    }

    public ImpossibleExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
