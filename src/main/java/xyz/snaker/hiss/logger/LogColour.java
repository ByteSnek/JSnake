package xyz.snaker.hiss.logger;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class LogColour
{
    public static final LogColour WHITE = new LogColour(250, false);
    public static final LogColour RED = new LogColour(203, false);
    public static final LogColour GREEN = new LogColour(32, true);
    public static final LogColour BLUE = new LogColour(34, true);
    public static final LogColour YELLOW = new LogColour(33, true);
    public static final LogColour TURQUOISE = new LogColour(45, false);
    public static final LogColour ORANGE = new LogColour(208, false);

    private final boolean legacy;
    private final String value;

    public LogColour(int value, boolean legacy, Style... styles)
    {
        this.legacy = legacy;
        this.value = compute(value, styles);
    }

    public LogColour(int value, boolean legacy)
    {
        this.legacy = legacy;
        this.value = compute(value);
    }

    public LogColour(int value)
    {
        this.legacy = false;
        this.value = compute(value);
    }

    public String compute(int value, Style... styles)
    {
        StringBuilder builder = new StringBuilder();

        applyStyles(builder, styles);

        if (isValidColour(value)) {
            if (styles == null) {
                if (legacy) {
                    return applyLegacy(value);
                } else {
                    return apply256(value);
                }
            }

            if (legacy) {
                return builder + applyLegacy(value);
            } else {
                return builder + apply256(value);
            }
        }

        return applyLegacy(37);
    }

    public boolean isValidColour(int value)
    {
        return value >= 0 && value <= 255;
    }

    public String getValue()
    {
        return value;
    }

    static String apply256(int value)
    {
        return "\u001b[38;5;" + value + "m";
    }

    static String applyLegacy(int value)
    {
        return "\u001b[" + value + "m";
    }

    static void applyStyles(StringBuilder builder, Style... styles)
    {
        if (styles != null) {
            for (Style style : styles) {
                builder.append(style.getValue());
            }
        }
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
            this.value = applyLegacy(value);
        }

        public String getValue()
        {
            return value;
        }
    }
}
