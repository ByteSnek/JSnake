package xyz.snaker.jsnake.utility;

import xyz.snaker.jsnake.logger.LogColour;
import xyz.snaker.jsnake.sneaky.Sneaky;

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

    public static <V> void writeToStorage(V value, File file, boolean openOnFinish)
    {
        writeToStorage(value.toString(), file.isAbsolute() ? file.toPath() : file.getAbsoluteFile().toPath(), openOnFinish);
    }

    public static <V> void writeToStorage(V value, Path path, boolean openOnFinish)
    {
        writeToStorage(value, path.isAbsolute() ? path.toString() : path.toAbsolutePath().toString(), openOnFinish);
    }

    public static <V> void writeToStorage(V value, String path, boolean openOnFinish)
    {
        writeToStorage(value.toString(), path, openOnFinish);
    }

    public static void writeToStorage(String data, File file, boolean openOnFinish)
    {
        writeToStorage(data, file.isAbsolute() ? file.toPath() : file.getAbsoluteFile().toPath(), openOnFinish);
    }

    public static void writeToStorage(String data, Path path, boolean openOnFinish)
    {
        writeToStorage(data, path.isAbsolute() ? path.toString() : path.toAbsolutePath().toString(), openOnFinish);
    }

    public static void writeToStorage(String data, String name, boolean openOnFinish)
    {
        String storage = System.getProperty("jsnake.storage");
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
            byte[] bytes = data.getBytes();

            stream.write(bytes);
            stream.flush();
            stream.close();

            if (openOnFinish) {
                IOUtilities.openUrl(path.getParent().toString());
            }

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

    public static void openUrl(String path)
    {
        try {
            String command;

            if (Sneaky.isWindowsOS()) {
                command = String.format("explorer %s", path);
            } else {
                command = String.format("xdg-open %s", path);
            }

            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
