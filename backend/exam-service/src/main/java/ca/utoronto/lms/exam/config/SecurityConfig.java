package ca.utoronto.lms.exam.config;

import ca.utoronto.lms.shared.security.AuthenticationTokenFilter;
import ca.utoronto.lms.shared.security.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationTokenFilter authenticationTokenFilter)
            throws Exception {
        return http
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/api/exam-service/exams/**",
                        "/api/exam-service/exam-periods/**",
                        "/api/exam-service/exam-terms/**",
                        "/api/exam-service/exam-types/**").permitAll()
                .antMatchers(
                        HttpMethod.GET,
                       "/api/exam-service/exam-realizations/exam-term/*").hasAuthority(SecurityUtils.ROLE_TEACHER)
                .anyRequest().hasAuthority(SecurityUtils.ROLE_ADMIN)
                .and()
                .build();
    }
}