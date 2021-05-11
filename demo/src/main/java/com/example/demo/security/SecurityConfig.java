package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    private final JwtTokenServices jwtTokenServices;

    public SecurityConfig(JwtTokenServices jwtTokenServices){this.jwtTokenServices = jwtTokenServices;}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .requestMatchers()
//                .antMatchers("/api/v1/user/**")
//                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/login").permitAll() // allowed by anyone
                .antMatchers("/api/v1/registration/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/companies/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/companies/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/todo/list/").hasRole("USER")// allowed only when signed in
//                .antMatchers(HttpMethod.DELETE, "/api/todo/list/**").hasRole("ADMIN") // allowed if signed in with ADMIN role
//                .antMatchers(HttpMethod.PATCH, "/api/todo/add/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/todo/**").hasRole("ADMIN")
                .anyRequest().denyAll() // anything else is denied
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}