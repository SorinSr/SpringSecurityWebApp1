package com.example.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication().withUser(userBuilder.username("Ion").password("t123").roles("EMPLOYEE","ADMIN"))
                                     .withUser(userBuilder.username("Maria").password("t123").roles("MANAGER"))
                                     .withUser(userBuilder.username("Ana").password("t123").roles("ADMIN"));
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and()
//                .formLogin().loginPage("/showCustomLoginForm").loginProcessingUrl("/authThisUser").permitAll();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.anyRequest().authenticated()
                .antMatchers("/").permitAll()  // allow public access to home page
                .antMatchers("/homePage").authenticated()   //*** basic user/any user without roles
                .antMatchers("/employees/**").hasRole("EMPLOYEE")
                .antMatchers("/leaders/**").hasRole("MANAGER")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/showFancyLoginForm").loginProcessingUrl("/authThisUser").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/customAccessDenied")
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
        // after logout then redirect to landing page (root)
    }
}



