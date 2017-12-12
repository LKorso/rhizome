package com.rhizome.services.configuration;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ServicesConfiguration {

    @Bean
    public PropertiesFactoryBean errorsMessages() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("errors/error-messages.properties"));
        return bean;
    }
}
