package bytesnek.hiss.throwable;

import bytesnek.hiss.system.DLL;

/**
 * Throws when an attempt is made to load a {@link DLL} that has already been loaded by the System
 * <p>
 * Created by SnakerBone on 1/12/2023
 **/
public class LibraryAlreadyLoadedException extends RuntimeException
{
    public LibraryAlreadyLoadedException()
    {
        super();
    }

    public LibraryAlreadyLoadedException(String message)
    {
        super(message);
    }

    public LibraryAlreadyLoadedException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public LibraryAlreadyLoadedException(Throwable cause)
    {
        super(cause);
    }

    public LibraryAlreadyLoadedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
