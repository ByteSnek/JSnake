package xyz.snaker.hiss.utility;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public class ExecuteOnce
{
    private final AtomicBoolean value = new AtomicBoolean();

    public boolean execute()
    {
        return !value.getAndSet(true);
    }

    public void reset()
    {
        value.getAndSet(false);
    }
}
