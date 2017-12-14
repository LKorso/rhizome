package com.rhizome.web.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.rhizome.web.security.AuthenticationExceptionsHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                    .antMatchers("/html/registration.html", "/js/**", "/css/**", "/webjars/**").permitAll()
                    .mvcMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/")
                        .passwordParameter("password")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .failureHandler(authenticationExceptionsHandler())
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Bean
    public AuthenticationFailureHandler authenticationExceptionsHandler() {
        return new AuthenticationExceptionsHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception{
        authBuilder.userDetailsService(userDetailsService);
    }

}