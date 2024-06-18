package com.fivepanels.application.security;

import com.fivepanels.application.security.roles.Roles;
import com.fivepanels.application.views.user.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration
@EnableWebSecurity // <1>
@Configuration
public class SecurityConfig extends VaadinWebSecurity { // <2>

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Delegating the responsibility of general configurations
        // of http security to the super class. It's configuring
        // the followings: Vaadin's CSRF protection by ignoring
        // framework's internal requests, default request cache,
        // ignoring public views annotated with @AnonymousAllowed,
        // restricting access to other views/endpoints, and enabling
        // NavigationAccessControl authorization.
        // You can add any possible extra configurations of your own
        // here (the following is just an example):

        // http.rememberMe().alwaysRemember(false);

        // Configure your static resources with public access before calling
        // super.configure(HttpSecurity) as it adds final anyRequest matcher
        http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/login"))
                .permitAll());

        super.configure(http);

        // This is important to register your login view to the
        // navigation access control mechanism:
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Customize your WebSecurity configuration.
        super.configure(web);
    }

    /**
     * Demo UserDetailsManager which only provides two hardcoded
     * in memory users and their roles.
     * NOTE: This shouldn't be used in real world applications.
     */

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth ->
//                auth.requestMatchers(
//                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/login")).permitAll());  // <3>
//        super.configure(http);
//        setLoginView(http, LoginView.class); // <4>
//    }

//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//
//        http.authorizeRequests()
//                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
//                .anyRequest().authenticated();
//
//        // Set the login view
//        setLoginView(http, LoginView.class);
//    }



    //@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable) // CSRF DISABLE
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll() // Permitted den Login Access
//                        .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
//                        .anyRequest().authenticated()
//                ).httpBasic(AbstractHttpConfigurer::disable)
//                .logout(logoutConfig -> {
//                    logoutConfig.logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
//                })
//                .formLogin(login -> login.loginPage("/login").permitAll());
//
//        return http.build();
//    }






    @Bean
    public UserDetailsService users() {
        var alice = User.builder()
                .username("alice")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles(Roles.USER)
                .build();
        var bob = User.builder()
                .username("bob")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles(Roles.USER)
                .build();
        var admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles(Roles.ADMIN, Roles.USER)
                .build();
        return new InMemoryUserDetailsManager(alice, bob, admin);
    }
}
