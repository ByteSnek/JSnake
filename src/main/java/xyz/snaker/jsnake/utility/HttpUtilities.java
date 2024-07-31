package xyz.snaker.jsnake.utility;

import org.w3c.dom.Document;
import xyz.snaker.jsnake.logger.Logger;
import xyz.snaker.jsnake.logger.Loggers;

import javax.annotation.Nullable;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by SnakerBone on 29/07/24
 * <p>
 * Licensed under MIT
 **/
public class HttpUtilities
{
    public static final Logger LOGGER = Loggers.getLogger();

    public static Document getXmlDocument(String address)
    {
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            InputStream stream = connection.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            return builder.parse(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getXmlContents(Document document)
    {
        try {
            DOMSource source = new DOMSource(document);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            transformer.transform(source, result);

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isWebsiteOperational(String address)
    {
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            boolean isValid = String.valueOf(responseCode).startsWith("2");

            Response.Status status = Response.Status.fromStatusCode(responseCode);

            if (isValid) {
                LOGGER.debugf("GET [] ([] [])", address, status.getStatusCode(), status.getReasonPhrase());
            } else {
                LOGGER.debugf("GET [] ([] [])", address, status.getStatusCode(), status.getReasonPhrase().toUpperCase());
            }

            return isValid;
        } catch (UnknownHostException | ProtocolException e) {
            LOGGER.warnf("GET [] (Invalid protocol or unknown host)", address);
            return false;
        } catch (IOException e) {
            LOGGER.warnf("GET [] ([])", address, e.getMessage());
            return false;
        }
    }

    public static String getRandomWord(RandomWordCategory category, @Nullable Character startingLetter)
    {
        try {
            URL url = getRandomWordApi(category, startingLetter);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            connection.disconnect();

            String contents = builder.toString();

            return contents.replaceAll("[\\[\\]\"]", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static URL getRandomWordApi(RandomWordCategory category, @Nullable Character startingLetter)
    {
        RuntimeException apiNotAvailable = new RuntimeException("Random word API is currently unavailable");

        try {
            String baseUrl = "https://random-word-form.herokuapp.com";
            String categoryUrl = String.format("%s%s", baseUrl, category);

            if (startingLetter != null) {
                categoryUrl = String.format("%s%s/%s", baseUrl, category, startingLetter);
            }

            if (HttpUtilities.isWebsiteOperational(categoryUrl)) {
                return new URL(categoryUrl);
            }
        } catch (IOException e) {
            throw apiNotAvailable;
        }

        throw apiNotAvailable;
    }

    public static void stopServerIfNeeded(String host, int port)
    {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("netstat -ano");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(String.format("%s:%s", host, port))) {
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

