package xyz.snaker.hiss.logger;

import java.util.function.Function;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class LogColour
{
    public static final LogColour WHITE = new LogColour(250);
    public static final LogColour RED = new LogColour(1);
    public static final LogColour GREEN = new LogColour(120);
    public static final LogColour BLUE = new LogColour(45);
    public static final LogColour YELLOW = new LogColour(226);
    public static final LogColour ORANGE = new LogColour(208);

    private final Function<Integer, String> colour = code -> "\u001b[38;5;" + code + "m";

    private final String value;

    public LogColour(int value, Style... styles)
    {
        this.value = apply(value, styles);
    }

    public LogColour(int value)
    {
        this.value = apply(value);
    }

    public String apply(int value, Style... styles)
    {
        StringBuilder builder = new StringBuilder();

        if (styles != null) {
            for (Style style : styles) {
                builder.append(style.value());
            }
        }

        if (isValidColour(value)) {
            return styles == null ? colour.apply(value) : builder + colour.apply(value);
        }

        return colour.apply(255);
    }

    public boolean isValidColour(int value)
    {
        return value >= 0 && value <= 255;
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

    public enum Style
    {
        RESET(0),
        BOLD(1),
        FAINT(2),
        ITALIC(3),
        UNDERLINE(4),
        BLINKING(5),
        INVERT(7),
        HIDDEN(8),
        STRIKETHROUGH(9);

        private final String value;

        Style(int value)
        {
            this.value = apply(value);
        }

        public String apply(int value)
        {
            return "\u001b[" + value + "m";
        }

        public String value()
        {
            return value;
        }
    }
}
