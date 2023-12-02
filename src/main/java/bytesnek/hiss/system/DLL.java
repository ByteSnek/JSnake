package bytesnek.hiss.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import bytesnek.hiss.logger.Logger;
import bytesnek.hiss.logger.Loggers;
import bytesnek.hiss.throwable.LibraryAlreadyLoadedException;

/**
 * Created by SnakerBone on 13/11/2023
 **/
public interface DLL extends DynamicObserver<DLL>, DynamicLoader<DLL>, DynamicWriter
{
    Logger LOGGER = Loggers.getLogger();
    String TMPDIR = System.getProperty("java.io.tmpdir");

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
        File file = makeTempFile();

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

    default File makeTempDirectory(String name)
    {
        File file = new File(TMPDIR, name);

        if (!file.exists()) {
            if (file.mkdir()) {
                LOGGER.infof("Made temporary directory: []", file.getAbsolutePath());
            }
        }

        file.deleteOnExit();

        return file;
    }

    default File makeTempFile()
    {
        File parent = makeTempDirectory(getLibraryName());

        try {
            return File.createTempFile(getLibraryName(), ".dll", parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default void checkLibs(String name)
    {
        if (libs().containsKey(name)) {
            throw new LibraryAlreadyLoadedException("Library '%s' already loaded".formatted(name));
        }
    }
}
