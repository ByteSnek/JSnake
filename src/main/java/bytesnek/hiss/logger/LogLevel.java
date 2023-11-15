package bytesnek.hiss.logger;

import xyz.snaker.snkr4j.LogColour;
import xyz.snaker.snkr4j.LogMarker;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class LogLevel
{
    public static final LogLevel INFO = new LogLevel(xyz.snaker.snkr4j.LogMarker.INFO, xyz.snaker.snkr4j.LogColour.WHITE);
    public static final LogLevel WARN = new LogLevel(xyz.snaker.snkr4j.LogMarker.WARN, xyz.snaker.snkr4j.LogColour.ORANGE);
    public static final LogLevel ERROR = new LogLevel(xyz.snaker.snkr4j.LogMarker.ERROR, xyz.snaker.snkr4j.LogColour.RED);

    private final xyz.snaker.snkr4j.LogMarker marker;
    private final xyz.snaker.snkr4j.LogColour colour;

    public LogLevel(xyz.snaker.snkr4j.LogMarker marker, xyz.snaker.snkr4j.LogColour colour)
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
