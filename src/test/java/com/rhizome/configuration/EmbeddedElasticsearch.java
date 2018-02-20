package com.rhizome.configuration;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

public class EmbeddedElasticsearch implements AutoCloseable {

    private static final String DEFAULT_FILE_STORAGE = "target/elasticsearch";

    private final String fileStorage;

    private final Node node;

    public EmbeddedElasticsearch() {
        this(DEFAULT_FILE_STORAGE);
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

    @Override
    public void close() throws Exception {
        node.close();
        deleteFileStorage();
    }

    Client getClient() {
        return node.client();
    }

    private void deleteFileStorage() {
        try {
            FileUtils.deleteDirectory(new File(fileStorage));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete data directory of embedded elasticsearch server", e);
        }
    }
}
