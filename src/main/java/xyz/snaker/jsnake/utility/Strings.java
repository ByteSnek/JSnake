package xyz.snaker.jsnake.utility;

import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;
import xyz.snaker.jsnake.thread.PeripheralException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SnakerBone on 15/08/2023
 **/
@SuppressWarnings("UnnecessaryUnicodeEscape")
public class Strings
{
    private static final Logger LOGGER = Loggers.getLogger();

    public static final String EMPTY = "";
    public static final String LINE_SEPARATOR = System.lineSeparator();

    public static final Map<Character, ScriptValue> SCRIPT_VALUES = Suppliers.make(new LinkedHashMap<>(), map -> {
        map.put('0', ScriptValue.of("\u2080", "\u2070"));
        map.put('1', ScriptValue.of("\u2081", "\u00B9"));
        map.put('2', ScriptValue.of("\u2082", "\u00B2"));
        map.put('3', ScriptValue.of("\u2083", "\u00B3"));
        map.put('4', ScriptValue.of("\u2084", "\u2074"));
        map.put('5', ScriptValue.of("\u2085", "\u2075"));
        map.put('6', ScriptValue.of("\u2086", "\u2076"));
        map.put('7', ScriptValue.of("\u2087", "\u2077"));
        map.put('8', ScriptValue.of("\u2088", "\u2078"));
        map.put('9', ScriptValue.of("\u2089", "\u2079"));

        map.put('a', ScriptValue.of("\u2090", "\u1D43"));
        map.put('b', ScriptValue.of(null, "\u1D47"));
        map.put('c', ScriptValue.of(null, "\u1D9C"));
        map.put('d', ScriptValue.of(null, "\u1D48"));
        map.put('e', ScriptValue.of("\u2091", "\u1D49"));
        map.put('f', ScriptValue.of(null, "\u1DA0"));
        map.put('g', ScriptValue.of(null, "\u1D4D"));
        map.put('h', ScriptValue.of("\u2095", "\u02B0"));
        map.put('i', ScriptValue.of(null, "\u2071"));
        map.put('j', ScriptValue.of(null, "\u02B2"));
        map.put('k', ScriptValue.of("\u2096", "\u1D4F"));
        map.put('l', ScriptValue.of("\u2097", "\u02E1"));
        map.put('m', ScriptValue.of("\u2098", "\u1D50"));
        map.put('n', ScriptValue.of("\u2099", "\u207F"));
        map.put('o', ScriptValue.of("\u2092", "\u1D52"));
        map.put('p', ScriptValue.of("\u209A", "\u1D56"));
        map.put('r', ScriptValue.of(null, "\u02B3"));
        map.put('s', ScriptValue.of("\u209B", "\u02E2"));
        map.put('t', ScriptValue.of("\u209C", "\u1D57"));
        map.put('u', ScriptValue.of(null, "\u1D58"));
        map.put('v', ScriptValue.of(null, "\u1D5B"));
        map.put('w', ScriptValue.of(null, "\u02B7"));
        map.put('x', ScriptValue.of(null, "\u02E3"));
        map.put('y', ScriptValue.of(null, "\u02B8"));

        map.put('A', ScriptValue.of(null, "\u1D2C"));
        map.put('B', ScriptValue.of(null, "\u1D2E"));
        map.put('D', ScriptValue.of(null, "\u1D30"));
        map.put('E', ScriptValue.of(null, "\u1D31"));
        map.put('G', ScriptValue.of(null, "\u1D33"));
        map.put('H', ScriptValue.of(null, "\u1D34"));
        map.put('I', ScriptValue.of(null, "\u1D35"));
        map.put('J', ScriptValue.of(null, "\u1D36"));
        map.put('K', ScriptValue.of(null, "\u1D37"));
        map.put('L', ScriptValue.of(null, "\u1D38"));
        map.put('M', ScriptValue.of(null, "\u1D39"));
        map.put('N', ScriptValue.of(null, "\u1D3A"));
        map.put('O', ScriptValue.of(null, "\u1D3C"));
        map.put('P', ScriptValue.of(null, "\u1D3E"));
        map.put('R', ScriptValue.of(null, "\u1D3F"));
        map.put('T', ScriptValue.of(null, "\u1D40"));
        map.put('U', ScriptValue.of(null, "\u1D41"));
        map.put('V', ScriptValue.of(null, "\u2C7D"));
        map.put('W', ScriptValue.of(null, "\u1D42"));
    });

    public static String subscript(String value)
    {
        StringBuilder builder = new StringBuilder();

        for (char character : value.toCharArray()) {
            if (SCRIPT_VALUES.containsKey(character)) {
                builder.append(SCRIPT_VALUES.get(character).sub());
            }
        }

        return builder.toString();
    }

    public static String superscript(String value)
    {
        StringBuilder builder = new StringBuilder();

        for (char character : value.toCharArray()) {
            if (SCRIPT_VALUES.containsKey(character)) {
                builder.append(SCRIPT_VALUES.get(character).sup());
            }
        }

        return builder.toString();
    }

    /**
     * Removes an extension from a string
     * <p>
     * For example these strings:
     * <pre>
     *     object.json
     *     image.png
     *     text.txt
     * </pre>
     * Would be converted to:
     * <pre>
     *     object
     *     image
     *     text
     * </pre>
     *
     * @param string The string to strip the extension from
     * @return The stripped string
     */
    public static String stripExtension(String string)
    {
        if (!string.contains(".")) {
            PeripheralException.invoke(new RuntimeException("String '%s' does not seem to contain an extension"));
        }

        return string.substring(0, string.lastIndexOf('.'));
    }

    /**
     * Gives a string consisting of the elements of a given array of strings, each separated by a given separator
     * string
     *
     * @param pieces    The strings to join
     * @param separator The separator
     * @return The joined string
     */
    public static String join(String[] pieces, String separator)
    {
        return join(Arrays.asList(pieces), separator);
    }

    /**
     * Gives a string consisting of the string representations of the elements of a given array of objects,
     * each separated by a given separator string
     *
     * @param pieces    The elements whose string representations are to be joined
     * @param separator The separator
     * @return The joined string
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
            String[] regex = string.trim().split("\\s|\\p{Pc}");
            Predicate<String> notEmpty = word -> !word.isEmpty();
            Function<String, String> mapper = word -> word.substring(0, 1).toUpperCase() + word.substring(1);
            Collector<CharSequence, ?, String> collector = Collectors.joining(" ");

            return Stream.of(regex)
                    .filter(notEmpty)
                    .map(mapper)
                    .collect(collector);
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
