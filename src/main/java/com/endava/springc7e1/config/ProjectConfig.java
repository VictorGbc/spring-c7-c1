package com.endava.springc7e1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetails u1 = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .build();

        UserDetails u2 = User.withUsername("bill")
                .password("12345")
                .authorities("write")
                .build();

        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
        uds.createUser(u1);
        uds.createUser(u2);

        auth.userDetailsService(uds)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /*  by default
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
        */

        http.httpBasic();
        http.authorizeRequests()//.anyRequest()
                .antMatchers(HttpMethod.GET, "/hello")
                .access("hasAnyAuthority('write')")
                .anyRequest().permitAll();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails u1 = User.withUsername("john")
                .password("12345")
                .authorities("read", "write")
                .build();

        UserDetails u2 = User.withUsername("bill")
                .password("12345")
                .authorities("read", "write")
                .build();

        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
        uds.createUser(u1);
        uds.createUser(u2);

        return uds;
    }*/

/*    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/
}
