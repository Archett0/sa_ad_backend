package sg.edu.nus.ad_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sg.edu.nus.ad_backend.security.principal.CustomUserDetailService;
import sg.edu.nus.ad_backend.security.token.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // use custom request filter to replace the Spring one
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .logout(LogoutConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        // allow access login api
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        // allow member to access register api
                        .requestMatchers(HttpMethod.POST, "/api/member").permitAll()
                        // allow random books before login
                        .requestMatchers(HttpMethod.GET, "/api/book/random").permitAll()
                        // allow error page
                        .requestMatchers("/error").permitAll()
                        // allow swagger
                        .requestMatchers("/api.html").permitAll()
                        .requestMatchers("/swagger**/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
