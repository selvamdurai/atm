package com.atm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import static com.atm.security.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate","/","index","/js/*","/css/*").permitAll()
                .antMatchers("/api/**").hasRole(ACCOUNT_USER.name())
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ADMIN_USER.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ADMIN_USER.name())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ADMIN_USER.name())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ACCOUNT_USER.name(), ADMIN_USER.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
         UserDetails customer1 =  User.builder()
                .username("123456789")
                .password( passwordEncoder.encode( "1234"))
                .roles(ACCOUNT_USER.name())// ROLE_STUDENT
                .build();

         UserDetails customer2 =  User.builder()
                 .username("987654321")
                 .password(passwordEncoder.encode("4321"))
                 .roles(ACCOUNT_USER.name()) //ROLE_ACCOUNT_USER
                 .build();

        UserDetails adminUser =  User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password123"))
                .roles(ADMIN_USER.name()) //ROLE_ADMIN_USER
                .build();

        return new InMemoryUserDetailsManager(
                customer1,
                customer2,
                adminUser
        );
    }
}
