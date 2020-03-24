/*
package com.ril.svc.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Autowired
    DataSource dataSource;

 @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());
    }

 @Autowired
 public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
     auth.jdbcAuthentication().dataSource(dataSource)
             .passwordEncoder(new BCryptPasswordEncoder())
             .usersByUsernameQuery("select user_name,password,status from admin_user where user_name=?")
             .authoritiesByUsernameQuery("select username, role from role where username=?");
 }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/static/**","/assets/**","/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/client/store").permitAll()
                .antMatchers("/login1").permitAll()
                .antMatchers("/dashboard", "/user/getUserList")
                .hasAnyRole("ADMIN", "USER").anyRequest().authenticated().and().formLogin().loginPage("/login")
                .successHandler(customizeAuthenticationSuccessHandler)
                .permitAll().and().logout().permitAll();

        http.csrf().disable();
    }

 @Override
    public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        authenticationMgr.inMemoryAuthentication().withUser("saasadmin@ril.com").password(encoder.encode("admin"))
                .authorities("ROLE_ADMIN");
        authenticationMgr.inMemoryAuthentication().withUser("clientadmin@ril.com").password(encoder.encode("admin"))
                .authorities("ROLE_USER");
    }

}
*/
