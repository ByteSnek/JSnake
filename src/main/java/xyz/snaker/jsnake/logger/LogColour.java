package xyz.snaker.jsnake.logger;

/**
 * Created by SnakerBone on 4/11/2023
 **/
public class LogColour
{
    public static final LogColour BLACK = new LogColour(30, ColourDepth.FOUR_BIT);
    public static final LogColour RED = new LogColour(31, ColourDepth.FOUR_BIT);
    public static final LogColour GREEN = new LogColour(32, ColourDepth.FOUR_BIT);
    public static final LogColour BLUE = new LogColour(34, ColourDepth.FOUR_BIT);
    public static final LogColour YELLOW = new LogColour(33, ColourDepth.FOUR_BIT);
    public static final LogColour FUCHSIA = new LogColour(35, ColourDepth.FOUR_BIT);
    public static final LogColour CYAN = new LogColour(36, ColourDepth.FOUR_BIT);

    public static final LogColour WHITE = new LogColour(250, ColourDepth.EIGHT_BIT);
    public static final LogColour MAGENTA = new LogColour(161, ColourDepth.EIGHT_BIT);
    public static final LogColour PURPLE = new LogColour(126, ColourDepth.EIGHT_BIT);
    public static final LogColour ORANGE = new LogColour(208, ColourDepth.EIGHT_BIT);
    public static final LogColour TURQUOISE = new LogColour(45, ColourDepth.EIGHT_BIT);

    private final String value;
    private final ColourDepth depth;

    public LogColour(int value, ColourDepth depth, Style... styles)
    {
        this.depth = depth;
        this.value = compute(value, styles);
    }

    public LogColour(int value, ColourDepth depth)
    {
        this.depth = depth;
        this.value = compute(value);
    }

    public String compute(int value, Style... styles)
    {
        StringBuilder builder = new StringBuilder();

        applyStyles(builder, styles);

        switch (depth) {
            case FOUR_BIT -> {
                if (styles != null) {
                    return builder + format8to16(value);
                }

                return format8to16(value);
            }
            case EIGHT_BIT -> {
                if (styles != null) {
                    return builder + format256(value);
                }

                return format256(value);
            }
            case TRUE_COLOUR -> {
                if (styles != null) {
                    return builder + formatRgb(value);
                }

                return formatRgb(value);
            }
            default -> {
                return format8to16(37);
            }
        }
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return value;
    }

    static String format8to16(int value)
    {
        if (value != 0 && !(value >= 30 && value <= 39)) {
            String message = String.format(
                    "8-16 colour code '%s' is invalid. Visit %s for valid 8-16 colour codes",
                    value,
                    "https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797#8-16-colors"
            );

            throw new IllegalArgumentException(message);
        }

        return String.format("\u001b[1;%sm", value);
    }

    static String format256(int value)
    {
        if (!(value >= 0 && value <= 255)) {
            String message = String.format(
                    "256 colour code '%s' is invalid. Visit %s for valid 256 colour codes",
                    value,
                    "https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797#256-colors"
            );

            throw new IllegalArgumentException(message);
        }

        return String.format("\u001b[38;5;%sm", value);
    }

    static String formatRgb(int value)
    {
        int r = (value & 0xFF) << 16;
        int g = (value & 0xFF) << 8;
        int b = value & 0xFF;

        return String.format("\u001b[38;2;%s;%s;%sm", r, g, b);
    }

    static void applyStyles(StringBuilder builder, Style... styles)
    {
        if (styles != null) {
            for (Style style : styles) {
                builder.append(style.getValue());
            }
        }
    }

    public enum ColourDepth
    {
        FOUR_BIT,
        EIGHT_BIT,
        TRUE_COLOUR
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
            this.value = formatStyle(value);
        }

        static String formatStyle(int value)
        {
            if (!(0 <= value && value <= 9)) {
                String message = String.format(
                        "Style '%s' is invalid. Visit %s for valid styling codes",
                        value,
                        "https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797#colors--graphics-mode"
                );

                throw new IllegalArgumentException(message);
            }

            return String.format("\u001b[%sm", value);
        }

        public String getValue()
        {
            return value;
        }
    }
}
