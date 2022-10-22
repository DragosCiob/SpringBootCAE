package ro.siit.SpringBootCAE.security;

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

                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .antMatchers("/requests/*").authenticated()
                                .anyRequest().permitAll()
                                .and()
                                .formLogin()
                                .usernameParameter("username")
                                .defaultSuccessUrl("/requests")
                                .permitAll()
                                .and()
                                .logout().logoutSuccessUrl("/loginForm").permitAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .csrf().disable();
                // .httpBasic(withDefaults());
        return http.build();
    }



























//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//            .authorizeRequests()
//                .antMatchers("/anonymous").anonymous()
//                .antMatchers("/","/login*").permitAll()
//                .antMatchers("/assets/css/**", "assets/js/**", "/images/**").permitAll()
//                .antMatchers("/index*").permitAll()
//                .anyRequest().authenticated()
//
//                .and()
//                .formLogin()
//                .loginPage("/loginForm")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/logged", true);
//
//
//
//        return http.build();
//    }
//
//
//        protected void configure(final AuthenticationManagerBuilder auth ) throws Exception{
//        auth.inMemoryAuthentication()
//                .withUser( "dragos").password("pass").roles("USER");
//    }
//
////    protected void configure(final AuthenticationManagerBuilder auth ) throws Exception{
////        auth.inMemoryAuthentication()
////                .withUser( "dragos").password(passwordEncoder().encode("pass")).roles("USER");
////    }
////////@Bean
//////    private PasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }


}
