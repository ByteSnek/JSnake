package xyz.snaker.jsnake.utility;

import xyz.snaker.jsnake.logger.LogColour;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by SnakerBone on 29/07/24
 * <p>
 * Licensed under MIT
 **/
public class IOUtilities
{
    public static <MSG> void printWithColour(MSG message, PrintStream destination, LogColour colour)
    {
        String value = colour.getValue();
        String reset = LogColour.Style.RESET.getValue();

        destination.printf("%s%s%n%s", value, message, reset);
    }

    public static void writeToStorage(String string, String name)
    {
        String storage = System.getProperty("jsnake.storage");

        if (storage == null) {
            IOUtilities.setIOProperties();

            storage = System.getProperty("jsnake.storage");
        }

        Path path = Path.of(storage, name + ".jsnake").toAbsolutePath();

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            OutputStream stream = new FileOutputStream(path.toFile());
            byte[] bytes = string.getBytes();

            stream.write(bytes);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFromStorage(String name)
    {
        String storage = System.getProperty("jsnake.storage");
        Path path = Path.of(storage, name + ".jsnake").toAbsolutePath();

        if (!Files.exists(path)) {
            return null;
        }

        try (InputStream stream = new FileInputStream(path.toFile())) {
            StringBuilder builder = new StringBuilder();
            int character;

            while ((character = stream.read()) != -1) {
                builder.append((char) character);
            }

            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openUriConnection(String path)
    {
        try {
            String command = String.format("explorer %s", path);

            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setIOProperties()
    {
        String tmpdir = System.getProperty("java.io.tmpdir");
        Path path = Path.of(tmpdir, "jsnake").toAbsolutePath();

        System.setProperty("jsnake.storage", path.toString());
    }
}
