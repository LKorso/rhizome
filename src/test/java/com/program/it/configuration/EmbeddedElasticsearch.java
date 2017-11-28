package com.program.it.configuration;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import java.io.File;
import java.io.IOException;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class EmbeddedElasticsearch {

    private static final String DEFAUL_FILE_STORAGE = "target/elasticsearch";

    private final String fileStorage;

    private final Node node;

    public EmbeddedElasticsearch() {
        this(DEFAUL_FILE_STORAGE);
    }

    public EmbeddedElasticsearch(String fileStoragePath) {
        this.fileStorage = fileStoragePath;

        Settings.Builder settings = nodeBuilder().getSettings()
                .put("http.enabled", "false")
                .put("path.home", fileStorage);

        node = nodeBuilder()
                .local(true)
                .settings(settings)
                .node();
    }

    public Client getClient() {
        return node.client();
    }

    public void shutdown() {
        node.close();
        deleteFileStaroge();
    }

    private void deleteFileStaroge() {
        try {
            FileUtils.deleteDirectory(new File(fileStorage));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete data directory of embedded elasticsearch server", e);
        }
    }

}
