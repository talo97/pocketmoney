//package com.ioproject.pocketmoney.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    CustomUserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin").hasRole("admin")
//                .antMatchers("/api/**").hasRole("admin")
//                .antMatchers("/user").hasAnyRole("admin", "default")
//                .antMatchers("/").permitAll()
//                .and().formLogin();
////                .loginPage("/login"); //tak mozna nadpisac :)
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        //for test no password encryptor at all (maybe change later)
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//}
