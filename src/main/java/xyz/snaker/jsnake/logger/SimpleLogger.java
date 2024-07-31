package xyz.snaker.jsnake.logger;

import org.apache.commons.lang3.time.DurationFormatUtils;
import xyz.snaker.jsnake.utility.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class SimpleLogger implements Logger
{
    public static final String RESET = LogColour.Style.RESET.getValue();

    private final String name;
    private boolean active;

    private String timerName;
    private boolean timerActive;
    private long timerCount;

    public SimpleLogger(Class<?> clazz, boolean active)
    {
        this.name = trimClassName(clazz);
        this.active = active;

        LOGGERS.put(clazz, this);
    }

    @Override
    public <MSG> void info(MSG message)
    {
        print(message, LogLevel.INFO);
    }

    @Override
    public <MSG> void debug(MSG message)
    {
        print(message, LogLevel.DEBUG);
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
            write(level, message);
        }
    }

    @Override
    public void infof(String format, Object... args)
    {
        printf(format, LogLevel.INFO, args);
    }

    @Override
    public void debugf(String format, Object... args)
    {
        printf(format, LogLevel.DEBUG, args);
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

            write(level, builder);
        }
    }

    @Override
    public void startTimer(String name)
    {
        if (!timerActive) {
            timerName = name;
            timerActive = true;
            timerCount = System.currentTimeMillis();

            debugf("Started timer: []", name);
        }
    }

    @Override
    public void stopTimer()
    {
        if (timerActive) {
            long time = System.currentTimeMillis() - timerCount;
            String formattedTime = DurationFormatUtils.formatDuration(time, "s.S");

            debugf("Timer [] took []s", timerName, formattedTime);

            timerName = Strings.EMPTY;
            timerActive = false;
            timerCount = 0;
        }
    }

    @Override
    public boolean isActive()
    {
        return active;
    }

    @Override
    public void setActive(boolean active)
    {
        this.active = active;
    }

    private void write(LogLevel level, StringBuilder builder)
    {
        String colour = level.getColour().getValue();
        String marker = level.getMarker().getValue();

        String message = builder.toString();

        System.out.printf("%s[%s/%s]: %s%s%n", colour, name, marker, message, RESET);
    }

    private <MSG> void write(LogLevel level, MSG message)
    {
        String colour = level.getColour().getValue();
        String marker = level.getMarker().getValue();

        System.out.printf("%s[%s/%s]: %s%s%n", colour, name, marker, message, RESET);
    }

    private String trimClassName(Class<?> clazz)
    {
        String[] pieces = clazz.getName().split("\\.");
        List<String> strings = new ArrayList<>();

        Arrays.stream(pieces).forEach(piece ->
                {
                    int lastPieceIndex = pieces.length - 1;
                    String lastPiece = pieces[lastPieceIndex];

                    if (!piece.equals(lastPiece)) {
                        piece = piece.substring(0, 2);
                    }

                    strings.add(piece);
                }
        );

        return String.join(".", strings);
    }
}
