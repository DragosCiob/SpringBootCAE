package ro.siit.SpringBootCAE.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ro.siit.SpringBootCAE.services.CustomUserDetailsService;

@Configuration
public class ApplicationSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired private LoginSuccessHandler loginSuccessHandler;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .antMatchers("/caeLead/*").hasAuthority("CAELEAD")
                                .antMatchers("/user/*").hasAuthority("USER")
//                                .authenticated()
                                .anyRequest().permitAll()
                                .and()
                                .formLogin()
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login-error")
                                .usernameParameter("username")
                                .successHandler(loginSuccessHandler)
//                                .defaultSuccessUrl("/user/")
                                .permitAll()
                                .and()
                                .logout()
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login").permitAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .csrf().disable();
                // .httpBasic(withDefaults());
        return http.build();
    }


}
