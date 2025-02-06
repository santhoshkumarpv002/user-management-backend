package com.example.user_management_backend.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// public class JwtSecurityConfig {

//     @Autowired
//     private UserDetailsService userDetailsService;

//     @Autowired
//     private JwtFilter jwtFilter;

//     @Bean
//     public AuthenticationProvider authProvider() {
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setUserDetailsService(userDetailsService);
//         provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//         return provider;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//         http.csrf(customizer -> customizer.disable())
//                 .authorizeHttpRequests(request -> request
//                         .requestMatchers( "api/jwt/**", "/swagger-ui/index.html")
//                         .permitAll()

//                         // .requestMatchers("/api/users/all").hasRole("ADMIN")

//                         .anyRequest().authenticated())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     /*
//      * @Bean public UserDetailsService userDetailsService() {
//      *
//      * UserDetails user=User .withDefaultPasswordEncoder() .username("navin")
//      * .password("n@123") .roles("USER") .build();
//      *
//      * UserDetails admin=User .withDefaultPasswordEncoder() .username("admin")
//      * .password("admin@789") .roles("ADMIN") .build();
//      *
//      * return new InMemoryUserDetailsManager(user,admin); }
//      */

// }






// this contain cros and jwt both



import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }



@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(customizer -> customizer.disable())
        .cors(customizer -> customizer.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(Arrays.asList("*"));
            config.setExposedHeaders(Arrays.asList("Authorization"));
            config.setAllowCredentials(true);
            return config;
        }))
        .authorizeHttpRequests(request -> request
            .requestMatchers("api/jwt/**", "/swagger-ui/index.html", "/error", "/h2-console/**").permitAll() // Allow H2 console access
            .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .headers(headers -> headers.frameOptions().sameOrigin()); // Necessary to allow H2 console frames

    return http.build();
}


    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

