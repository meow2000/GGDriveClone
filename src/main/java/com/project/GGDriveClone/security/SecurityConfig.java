package com.project.GGDriveClone.security;

import com.project.GGDriveClone.enums.Role;
import com.project.GGDriveClone.jwt.JwtAuthenticationFilter;
import com.project.GGDriveClone.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.List;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger Logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Value("${application.enable-security}")
    private boolean enableSecurity;

    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager Bean
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
//        if(enableSecurity){
//            Logger.info("Enable application security.");
//            http.authorizeRequests()
//                    .antMatchers("/admin/**").hasRole(Role.ADMIN.getName())
//                    .anyRequest()
//                    .authenticated();
//        } else{
//            Logger.info("Disable application security.");
//            http.authorizeRequests().anyRequest().permitAll();
//        }
//
//        http.formLogin()
////                .loginPage("/login")
//                .permitAll()
////                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/admin")
//                .usernameParameter("username")
//                .passwordParameter("password");
//
//        http.csrf().disable();
//    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**", "/templates/**", "/js/**", "/icon/**", "/images/**", "/favicon.ico/**",
                "/webjars/springfox-swagger-ui/**", "/swagger-ui.html/**", "/swagger-resources/**", "/v1/api-docs");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (enableSecurity) {
            Logger.info("Enable application security.");
            http.authorizeRequests()
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.getName())
                    .antMatchers("/api/login").permitAll()
                    .anyRequest()
                    .authenticated();
        } else {
            Logger.info("Disable application security.");
            http.authorizeRequests().anyRequest().permitAll();
        }

        http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        });

        http.formLogin()
                .permitAll()
                .defaultSuccessUrl("/admin/")
                .usernameParameter("username")
                .passwordParameter("password");

        http.csrf().disable();

        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
//   http.authorizeRequests()
//           .antMatchers("").authenticated()
//           .anyRequest().permitAll()
//           .and()
//           .formLogin()
//           .usernameParameter("email")
//           .defaultSuccessUrl("/")
//           .permitAll()
//           .and()
//           .logout().logoutSuccessUrl("/").permitAll();