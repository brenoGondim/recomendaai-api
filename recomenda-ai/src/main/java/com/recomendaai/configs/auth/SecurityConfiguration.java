//package com.recomendaai.configs.auth;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.OrRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@Order(1) // this is important to run this before Spring OAuth2
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        List<RequestMatcher> requestMatchers = new ArrayList<RequestMatcher>();
//        // allow /api/public/product/** and /api/public/content/** not intercepted by Spring OAuth2
//        requestMatchers.add(new AntPathRequestMatcher("/offsers/**"));
//        //requestMatchers.add(new AntPathRequestMatcher("/api/public/content/**"));
//
//        http
//                .requestMatcher(new OrRequestMatcher(requestMatchers))
//                .authorizeRequests()
//                .antMatchers("/api/public/product/**", "/api/public/content/**").permitAll();
//    }
//}
