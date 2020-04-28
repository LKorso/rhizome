package com.rhizome.configuration;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
@ComponentScan(
        basePackages = {
                "com.rhizome.repositories",
                "com.rhizome.services"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        Client.class,
                        ElasticsearchTemplate.class
                }
        )
)
public class EmbeddedElasticsearchConfiguration {

    @Bean
    public EmbeddedElasticsearch embeddedElasticsearch() {
        return new EmbeddedElasticsearch();
    }

    @Bean
    public Client clientForEmbeddedDb() {
        return embeddedElasticsearch().getClient();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(clientForEmbeddedDb());
    }

}
