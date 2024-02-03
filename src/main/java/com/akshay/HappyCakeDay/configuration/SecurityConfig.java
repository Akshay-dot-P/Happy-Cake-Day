package com.akshay.HappyCakeDay.configuration;

import com.akshay.HappyCakeDay.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    CustomUserDetailsService customUserDetail;
    @Autowired
    google0Auth2SuccessHandler google0auth2SuccessHandler;
/*
    @Bean
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetail);
    }

*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetail);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }



    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                CommonOAuth2Provider.GOOGLE.getBuilder("google")
                        .clientId("405181235427-8qfvnu00qmp4p9r95fj7euas5nm3ed7g.apps.googleusercontent.com")
                        .clientSecret("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")
                        .build()
        );
    }
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers("/", "/shop","/cart/**","/cart","/shop/viewproduct/**","/addtocart/**", "/shop/**","/viewproduct/**", "/register","/adminus/**","adminusrest/**","/static/**","/homepage/**", "/ProductImages/**", "/h2-console/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
               .authenticationProvider(daoAuthenticationProvider())
                .formLogin(form ->
                        form
                                .loginPage("/showlogin")
                                .loginProcessingUrl("/loginprocess")
                                .permitAll()
                                .failureUrl("/login?error = true")
                                .defaultSuccessUrl("/home", true)
                                .usernameParameter("email")
                                .passwordParameter("password")
                )

                .oauth2Login()
                .successHandler(google0auth2SuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/showlogin")
                .invalidateHttpSession(true)
                //.deleteCOOKIES("JSESSIONID")
                .and()
                .csrf()
                .disable();
        http.headers().frameOptions().disable();
        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }





    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**", "/css/**", "/js/**");

    }


}
