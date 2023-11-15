package bytesnek.hiss.logger;

import xyz.snaker.snkr4j.LogColour;
import xyz.snaker.snkr4j.LogLevel;
import xyz.snaker.snkr4j.SnakerLogger;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class SimpleLogger implements SnakerLogger
{
    private final String name;
    private volatile boolean active;

    public SimpleLogger(Class<?> clazz, boolean active)
    {
        this.name = clazz.getSimpleName();
        this.active = active;
    }

    public SimpleLogger(String name, boolean active)
    {
        this.name = name;
        this.active = active;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public <MSG> void info(MSG message)
    {
        print(message, LogLevel.INFO);
    }

    @Override
    public <MSG> void warn(MSG message)
    {
        print(message, LogLevel.WARN);
    }

    @Override
    public <MSG> void error(MSG message)
    {
        print(message, LogLevel.ERROR);
    }

    @Override
    public <MSG> void print(MSG message, LogLevel level)
    {
        if (active) {
            System.out.println(level.colour().value() + "[" + name + "/" + level.marker().value() + "]: " + message + LogColour.WHITE.value() + LogColour.Style.RESET.value());
        }
    }

    @Override
    public void infof(String format, Object... args)
    {
        printf(format, LogLevel.INFO, args);
    }

    @Override
    public void warnf(String format, Object... args)
    {
        printf(format, LogLevel.WARN, args);
    }

    @Override
    public void errorf(String format, Object... args)
    {
        printf(format, LogLevel.ERROR, args);
    }

    @Override
    public void printf(String format, LogLevel level, Object... args)
    {
        if (active) {
            int argIndex = 0;
            StringBuilder builder = new StringBuilder(format);

            while (argIndex < args.length) {
                int index = builder.indexOf("[]");

                if (index == -1) {
                    break;
                }

                builder.replace(index, index + 2, args[argIndex].toString());
                argIndex++;
            }

            System.out.println(level.colour().value() + "[" + name + "/" + level.marker().value() + "]: " + builder + LogColour.WHITE.value() + LogColour.Style.RESET.value());
        }
    }
}
