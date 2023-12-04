package xyz.snaker.hiss.logger;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class LogLevel
{
    public static final LogLevel INFO = new LogLevel(LogMarker.INFO, LogColour.WHITE);
    public static final LogLevel WARN = new LogLevel(LogMarker.WARN, LogColour.ORANGE);
    public static final LogLevel ERROR = new LogLevel(LogMarker.ERROR, LogColour.RED);

    private final LogMarker marker;
    private final LogColour colour;

    public LogLevel(LogMarker marker, LogColour colour)
    {
        this.marker = marker;
        this.colour = colour;
    }

    public LogMarker marker()
    {
        return marker;
    }

    public LogColour colour()
    {
        return colour;
    }

    @Override
    public String toString()
    {
        return "LEVEL/" + marker.value();
    }
}
