package xyz.snaker.jsnake.system;

import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;
import xyz.snaker.jsnake.throwable.LibraryAlreadyLoadedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public interface DLL extends DynamicLink<DLL>, DynamicLoader<DLL>, DynamicWriter
{
    Logger LOGGER = Loggers.getLogger();
    String JSNAKE_STORAGE = System.getProperty("java.io.tmpdir") + "\\jsnake";

    String getLibraryName();

    @Override
    default Map<String, Class<? extends DLL>> libs()
    {
        return new LinkedHashMap<>();
    }

    @Override
    default void load(Class<? extends DLL> clazz, boolean logSuccess)
    {
        String dll = getLibraryName() + ".dll";

        checkLibs(dll);

        InputStream stream = clazz.getClassLoader().getResourceAsStream(dll);
        File file = createFile();

        write(stream, file);

        System.load(file.getAbsolutePath());

        libs().put(dll, clazz);

        if (logSuccess) {
            LOGGER.infof("Loaded []", dll);
        }
    }

    @Override
    default void write(InputStream stream, File file)
    {
        String dll = getLibraryName() + ".dll";

        if (stream == null) {
            throw new RuntimeException("No %s found in directory %s".formatted(dll, file.getAbsolutePath()));
        }

        try (FileOutputStream output = new FileOutputStream(file)) {
            byte[] bytes = new byte[1024];
            int length;

            while ((length = stream.read(bytes)) != -1) {
                output.write(bytes, 0, length);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default File createFile()
    {
        String name = getLibraryName();
        File parent = new File(JSNAKE_STORAGE);
        File file = new File(parent, String.format("%s.jsnake", name));

        if (!Files.exists(file.toPath())) {
            try {
                Files.createDirectories(file.toPath().getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return file.getAbsoluteFile();
    }

    default void checkLibs(String name)
    {
        if (libs().containsKey(name)) {
            throw new LibraryAlreadyLoadedException("Library '%s' already loaded".formatted(name));
        }
    }
}
