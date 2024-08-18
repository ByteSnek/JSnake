package xyz.snaker.jsnake.utility;

import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by SnakerBone on 29/07/24
 * <p>
 * Licensed under MIT
 **/
public class HttpUtilities
{
    public static final Logger LOGGER = Loggers.getLogger();

    public static void stopServerIfNeeded(String hostname, int port)
    {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("netstat -ano");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(String.format("%s:%s", hostname, port))) {
                    String[] parts = line.replaceAll("\\s+", " ").split("\\s");
                    String pid = parts[parts.length - 1];
                    String command = String.format("taskkill /F /PID %s", pid);

                    runtime.exec(command);

                    LOGGER.debugf("Killed server hosted on port []", port);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

