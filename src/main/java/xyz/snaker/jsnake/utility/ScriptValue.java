package xyz.snaker.jsnake.utility;

/**
 * Created by SnakerBone on 24/08/24
 **/
public class ScriptValue
{
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
