package com.grasshouse.dorandoran.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2SuccessHandler successHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable();

        http.
            authorizeRequests()
            .antMatchers("/login").authenticated()
            .and()
            .oauth2Login()
            .successHandler(successHandler);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
    }
}
