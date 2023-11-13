package bytesnek.hiss.utility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.snaker.snkr4j.SimpleLogger;
import xyz.snaker.snkr4j.SnakerLogger;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Strings
{
    private static final SnakerLogger LOGGER = new SimpleLogger(Strings.class, true);

    public static final String EMPTY = "";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Gives a string consisting of the elements of a given array of strings, each separated by a given separator
     * string.
     *
     * @param pieces    the strings to join
     * @param separator the separator
     * @return the joined string
     */
    public static String join(String[] pieces, String separator)
    {
        return join(Arrays.asList(pieces), separator);
    }

    /**
     * Gives a string consisting of the string representations of the elements of a given array of objects,
     * each separated by a given separator string.
     *
     * @param pieces    the elements whose string representations are to be joined
     * @param separator the separator
     * @return the joined string
     */
    public static String join(Iterable<String> pieces, String separator)
    {
        StringBuilder buffer = new StringBuilder();

        for (Iterator<String> iter = pieces.iterator(); iter.hasNext(); ) {
            buffer.append(iter.next());

            if (iter.hasNext()) {
                buffer.append(separator);
            }
        }

        return buffer.toString();
    }

    /**
     * Formats an I18N string
     * <p>
     * For example these strings:
     * <pre>
     *      hello_world
     *      hello
     *      world
     * </pre>
     * Would be formatted to:
     * <pre>
     *     Hello World
     *     Hello
     *     World
     * </pre>
     *
     * @param string The string to format
     * @return The formatted string
     **/
    public static String i18nt(String string)
    {
        if (!string.isEmpty()) {
            return Stream.of(string.trim().split("\\s|\\p{Pc}")).filter(word -> word.length() > 0).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));
        } else {
            return string;
        }
    }

    /**
     * Formats a string to a parseable I18N string
     * <p>
     * For example these strings:
     * <pre>
     *      Hello World
     *      Hello
     *      World
     * </pre>
     * Would be formatted to:
     * <pre>
     *     hello_world
     *     hello
     *     world
     * </pre>
     *
     * @param string The string to format
     * @return The formatted string
     **/
    public static String i18nf(String string)
    {
        return !string.isEmpty() ? string.replaceAll("\\s+", "_").toLowerCase() : string;
    }

    /**
     * Checks if a string is not null, empty and if it matches valid english regex
     *
     * @param string         The string to check
     * @param notify         Should be logged if the string is invalid
     * @param throwException Should throw exception if string is invalid
     * @return True if the string is valid
     **/
    public static boolean isValidString(String string, boolean notify, boolean throwException)
    {
        if (string == null || string.isEmpty()) {
            return false;
        } else {
            String regex = ".*[a-zA-Z]+.*";
            if (!string.matches(regex)) {
                if (notify) {
                    LOGGER.warnf("Found an invalid string: []", string);
                    if (throwException) {
                        throw new RuntimeException(String.format("Invalid string: %s", string));
                    }
                }
                if (!notify && throwException) {
                    throw new RuntimeException(String.format("Invalid string: %s", string));
                }
            }
            return string.matches(regex);
        }
    }

    /**
     * Checks if a string is not null, empty and if it matches valid english regex
     *
     * @param string The string to check
     * @param notify Should be logged if the string is invalid
     * @return True if the string is valid
     **/
    public static boolean isValidString(String string, boolean notify)
    {
        return isValidString(string, notify, false);
    }

    /**
     * Checks if a string is not null, empty and if it matches valid english regex
     *
     * @param string The string to check
     * @return True if the string is valid
     **/
    public static boolean isValidString(String string)
    {
        return isValidString(string, false);
    }
}
