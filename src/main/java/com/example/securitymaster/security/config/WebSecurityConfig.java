package com.example.securitymaster.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import static com.example.securitymaster.security.SecurityRoles.*;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Bean
    public UserDetailsService userDetailsService(){
        var uds = new InMemoryUserDetailsManager();

        var kyaw = User.withUsername("kyaw")
                .password("233255")
                .roles(ROLES_ADMIN)
                .build();

        var thet = User.withUsername("thet")
                .password("thet")
                .roles(CUSTOMERS_ADMIN)
                .build();

        var emma = User.withUsername("emma")
                .password("emma")
                .roles(EMPLOYEES_ADMIN)
                .build();

        var william = User.withUsername("william")
                .password("william")
                .roles(DEPARTMENTS_ADMIN)
                .build();

        var lucas = User.withUsername("lucas")
                .password("lucas")
                .roles(EMPLOYEES_PAG_VIEW,DEPARTMENTS_PAG_VIEW,CUSTOMERS_PAG_VIEW)
                .build();

        var tom = User.withUsername("tom")
                .password("tom")
                .roles()
                .build();

        uds.createUser(lucas);
        uds.createUser(emma);
        uds.createUser(tom);
        uds.createUser(kyaw);
        uds.createUser(william);
        uds.createUser(thet);

        return uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Throwable{

        http.authorizeRequests()
                .expressionHandler(expressionHandler())
                .requestMatchers("/","/home","/bootstrap/**")
                .permitAll()
                .requestMatchers("/employee/**").hasRole(EMPLOYEES_PAG_VIEW)
                .requestMatchers("/customer/**").hasRole(CUSTOMERS_PAG_VIEW)
                .requestMatchers("/department/**").hasRole(DEPARTMENTS_PAG_VIEW)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler(){
        DefaultWebSecurityExpressionHandler handler =
                new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
