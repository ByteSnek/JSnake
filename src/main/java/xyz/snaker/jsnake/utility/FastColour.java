package xyz.snaker.jsnake.utility;

import xyz.snaker.jsnake.math.Maths;

/**
 * Created by SnakerBone on 18/08/24
 **/
public class FastColour
{
    public static int alpha(int value)
    {
        return value >>> 24;
    }

    public static int alpha(double value)
    {
        return Maths.floor(value) >>> 24;
    }

    public static int red(int value)
    {
        return value >> 16 & 0xFF;
    }

    public static int red(double value)
    {
        return Maths.floor(value) >> 16 & 0xFF;
    }

    public static int green(int value)
    {
        return value >> 8 & 0xFF;
    }

    public static int green(double value)
    {
        return Maths.floor(value) >> 8 & 0xFF;
    }

    public static int blue(int value)
    {
        return value & 0xFF;
    }

    public static int blue(double value)
    {
        return Maths.floor(value) & 0xFF;
    }

    public static int rgb(int red, int green, int blue)
    {
        return red << 16 | green << 8 | blue;
    }

    public static int rgb(double red, double green, double blue)
    {
        return Maths.floor(red) << 16 | Maths.floor(green) << 8 | Maths.floor(blue);
    }

    public static int rgba(int red, int green, int blue, int alpha)
    {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int rgba(double red, double green, double blue, double alpha)
    {
        return Maths.floor(alpha) << 24 | Maths.floor(red) << 16 | Maths.floor(green) << 8 | Maths.floor(blue);
    }
}
