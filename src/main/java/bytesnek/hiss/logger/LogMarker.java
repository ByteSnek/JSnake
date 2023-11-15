package bytesnek.hiss.logger;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class LogMarker
{
    public static final LogMarker INFO = new LogMarker("INFO");
    public static final LogMarker WARN = new LogMarker("WARN");
    public static final LogMarker ERROR = new LogMarker("ERROR");

    private final String value;

    public LogMarker(String value)
    {
        this.value = value.toUpperCase();
    }

    public String value()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
