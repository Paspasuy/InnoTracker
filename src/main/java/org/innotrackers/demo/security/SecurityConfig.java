/*
package org.innotrackers.demo.security;

import jakarta.servlet.Filter;
import org.innotrackers.demo.orm.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsernameSessionFilter usernameSessionFilter;
//    @Autowired
//    private CsrfTokenResponseFilter csrfTokenResponseFilter;

    @Bean
    public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                        .permitAll());
//                .csrf(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
                .httpBasic(withDefaults());

        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName(null);
*/
/*

        http.csrf(csrf -> csrf
//                .ignoringRequestMatchers("/api/user/photo")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                        .csrfTokenRequestHandler(requestAttributeHandler)
        );
*//*


//        http.addFilterAfter(new GetCsrfTokenFilter(), ExceptionTranslationFilter.class);
        http.addFilterAfter(usernameSessionFilter, SecurityContextHolderFilter.class);
//        http.addFilterAfter(csrfTokenResponseFilter, UsernameSessionFilter.class);

//        http.csrf(AbstractHttpConfigurer::disable);
*/
/*        http.cors((cors) -> cors.configurationSource(request -> {
            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.setAllowedOrigins(List.of("*")); // Add allowed origins
            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfig.setAllowCredentials(true);
            corsConfig.addAllowedHeader("*");
            return corsConfig;
        }));*//*

        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/api/public/*"));
    }

}

*/
