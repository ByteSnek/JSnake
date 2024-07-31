package xyz.snaker.jsnake.system;

import java.io.File;
import java.io.InputStream;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public interface DynamicWriter
{
    void write(InputStream stream, File file);
}
