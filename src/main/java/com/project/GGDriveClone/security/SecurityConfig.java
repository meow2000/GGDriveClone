package com.project.GGDriveClone.security;

import com.project.GGDriveClone.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Value("${application.enable-security}")
    private boolean enableSecurity;

    private Logger Logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**", "/libs/**", "/templates/**", "/js/**", "/icon/**", "/images/**", "/favicon.ico/**",
                "/webjars/springfox-swagger-ui/**", "/swagger-ui.html/**", "/swagger-resources/**", "/v1/api-docs", "/upload/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(enableSecurity){
            System.out.println("sssss");
            Logger.info("Enable application security.");
            http.authorizeRequests()
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.getName())
                    .anyRequest()
                    .authenticated();
        } else{
            Logger.info("Disable application security.");
            http.authorizeRequests().anyRequest().permitAll();
        }

        http.formLogin()
//                .loginPage("/login")
                .permitAll()
//                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin")
                .usernameParameter("username")
                .passwordParameter("password");

        http.csrf().disable();
    }
}
