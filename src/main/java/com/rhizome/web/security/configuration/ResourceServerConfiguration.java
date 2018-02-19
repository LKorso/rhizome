package com.rhizome.web.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

import com.rhizome.web.security.filters.CookieToHeaderFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CookieToHeaderFilter(), CsrfFilter.class)
                .authorizeRequests()
                    .antMatchers("/", "/html/registration.html", "/js/**", "/css/**", "/webjars/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().authenticated();
    }

}