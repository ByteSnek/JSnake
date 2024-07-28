package xyz.snaker.hiss.maven.artifact;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by SnakerBone on 28/07/24
 * <p>
 * Licensed under MIT
 **/
public class MavenArtifact implements MavenManifest
{
    private final String repositoryUrl;
    private final String groupId;
    private final String artifactId;
    private final String version;

    public MavenArtifact(String repositoryUrl, String groupId, String artifactId, String version)
    {
        this.repositoryUrl = repositoryUrl;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getJarUrl(@Nullable String identifier)
    {
        String groupIdUrl = groupId.replace('.', '/');
        String jarName = getJarName(identifier);

        return String.format("%s/%s/%s/%s/%s", repositoryUrl, groupIdUrl, artifactId, version, jarName);
    }

    public void downloadJar(@Nullable String identifier, File dest)
    {
        Path path = Paths.get(dest.getAbsolutePath());

        if (!Files.exists(path)) {
            mkdirs(path);
        } else {
            return;
        }

        String fileName = getJarName(identifier);
        Path filePath = path.resolve(fileName);

        String jarUrl = getJarUrl(identifier);
        URL url = newUrl(jarUrl);
        HttpURLConnection connection = openUrlConnection(url);

        try (InputStream in = new BufferedInputStream(connection.getInputStream()); FileOutputStream out = new FileOutputStream(filePath.toFile())) {
            byte[] bytes = new byte[1024];
            int bytesRead;

            int fileSize = connection.getContentLength();
            int totalBytesRead = 0;
            int progress = 0;

            while ((bytesRead = in.read(bytes)) != -1) {
                out.write(bytes, 0, bytesRead);
                totalBytesRead += bytesRead;

                int currentProgress = (int) (totalBytesRead * 100. / fileSize);

                if (currentProgress > progress) {
                    progress = currentProgress;

                    displayProgress(progress, identifier);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    public String getJarName(@Nullable String identifier)
    {
        if (identifier == null || identifier.isEmpty()) {
            return String.format("%s-%s.jar", artifactId, version);
        } else {
            return String.format("%s-%s-%s.jar", artifactId, version, identifier);
        }
    }

    private void displayProgress(int progress, @Nullable String identifier)
    {
        String jarUrl = getJarUrl(identifier);
        System.out.printf("\rDownloading %s (%s%% done)", jarUrl, progress);
    }

    private void mkdirs(Path path)
    {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL newUrl(String path)
    {
        try {
            return new URL(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpURLConnection openUrlConnection(URL url)
    {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getGroupId()
    {
        return groupId;
    }

    @Override
    public String getArtifactId()
    {
        return artifactId;
    }

    @Override
    public String getVersion()
    {
        return version;
    }
}
