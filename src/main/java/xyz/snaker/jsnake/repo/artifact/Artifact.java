package xyz.snaker.jsnake.repo.artifact;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import xyz.snaker.jsnake.utility.HttpUtilities;

import javax.annotation.Nullable;
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
public class Artifact implements Manifest
{
    private final String repositoryUrl;
    private final String groupId;
    private final String artifactId;
    private final ArtifactVersion version;

    private Artifact(String repositoryUrl, String groupId, String artifactId, ArtifactVersion version)
    {
        this.repositoryUrl = repositoryUrl;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getJarUrl(@Nullable String classifier)
    {
        String groupIdUrl = groupId.replace('.', '/');
        String jarName = getJarName(classifier);

        return String.format("%s/%s/%s/%s/%s", repositoryUrl, groupIdUrl, artifactId, version.getId(), jarName);
    }

    public void downloadJar(@Nullable String classifier, File dest)
    {
        Path path = Paths.get(dest.getAbsolutePath());

        if (!Files.exists(path)) {
            mkdirs(path.getParent());
        } else {
            return;
        }

        String jarUrl = getJarUrl(classifier);
        URL url = newUrl(jarUrl);
        HttpURLConnection connection = openUrlConnection(url);

        try (InputStream in = new BufferedInputStream(connection.getInputStream()); FileOutputStream out = new FileOutputStream(path.toString())) {
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

                    displayProgress(progress, classifier);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    public String getJarName(@Nullable String classifier)
    {
        if (classifier == null || classifier.isEmpty()) {
            return String.format("%s-%s.jar", artifactId, version.getId());
        } else {
            return String.format("%s-%s-%s.jar", artifactId, version.getId(), classifier);
        }
    }

    private void displayProgress(int progress, @Nullable String classifier)
    {
        String jarUrl = getJarUrl(classifier);
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
    public ArtifactVersion getVersion()
    {
        return version;
    }

    public static class Builder
    {
        private final String repositoryUrl;
        private String groupId;
        private String artifactId;
        private ArtifactVersion version;

        public Builder(String repositoryUrl)
        {
            this.repositoryUrl = repositoryUrl;
        }

        public Builder setGroupId(String groupId)
        {
            this.groupId = groupId;
            return this;
        }

        public Builder setArtifactId(String artifactId)
        {
            this.artifactId = artifactId;
            return this;
        }

        public Builder setVersion(ArtifactVersion version)
        {
            this.version = version;
            return this;
        }

        public Builder setVersion(String version)
        {
            return setVersion(ArtifactVersion.of(version));
        }

        public Builder useLatestVersion()
        {
            if (repositoryUrl == null || repositoryUrl.isEmpty()) {
                throw new IllegalArgumentException("Repository URL is not valid");
            }

            if (groupId == null || groupId.isEmpty() || artifactId == null || artifactId.isEmpty()) {
                throw new IllegalStateException("MavenArtifact.Builder.setLatestVersion() must be called directly BEFORE you call MavenArtifact.Builder.build()");
            }

            String address = getMetadataXmlAddress(repositoryUrl, groupId, artifactId);
            Document document = HttpUtilities.getXmlDocument(address);

            if (document != null) {
                String latestVersion = getLatestVersion(document);

                return setVersion(latestVersion);
            }

            throw new RuntimeException(String.format("Invalid or no maven-metadata.xml document found at web address %s", address));
        }

        public Artifact build()
        {
            return new Artifact(repositoryUrl, groupId, artifactId, version);
        }

        private String getMetadataXmlAddress(String repositoryUrl, String groupId, String artifactId)
        {
            String groupIdUrl = groupId.replace('.', '/');

            return String.format("%s/%s/%s/maven-metadata.xml", repositoryUrl, groupIdUrl, artifactId);
        }

        private String getLatestVersion(Document document)
        {
            try {
                NodeList list = document.getElementsByTagName("latest");

                if (list.getLength() > 0) {
                    return list.item(0).getTextContent();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            throw new RuntimeException("Inavlid metadata.xml file. Could not find <latest> tag");
        }
    }
}
