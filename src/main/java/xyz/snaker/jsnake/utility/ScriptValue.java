package xyz.snaker.jsnake.utility;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SnakerBone on 24/08/24
 **/
public class ScriptValue
{
    @SuppressWarnings("UnnecessaryUnicodeEscape")
    public static final Map<Character, ScriptValue> BY_CHAR = Suppliers.make(new LinkedHashMap<>(), map -> {
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

    private final String sub;
    private final String sup;

    public ScriptValue(String sub, String sup)
    {
        this.sub = sub;
        this.sup = sup;
    }

    public static ScriptValue of(String sub, String sup)
    {
        return new ScriptValue(sub, sup);
    }

    public String sub()
    {
        return sub == null ? "?" : sub;
    }

    public String sup()
    {
        return sup == null ? "?" : sup;
    }
}
