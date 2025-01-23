package com.circleaf.circleaf_api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.circleaf.circleaf_api.constant.Constants;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .cors()
        .and()
        .csrf().disable() // CSRF保護を無効化
        .formLogin()
            .loginProcessingUrl("/login") // Reactからのリクエストを受け取るURL
            .usernameParameter("username") // フロントエンドから送信するパラメータ名
            .passwordParameter("password") // フロントエンドから送信するパラメータ名
            .successHandler((request, response, authentication) -> {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Login successful\"}");
                // CookieのSameSite属性を設定
                response.addHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; Path=/; HttpOnly; SameSite=None; Secure");
            })
            .failureHandler((request, response, exception) -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Login failed\"}");
            })
        .and()
        .logout()
        .and()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/new").authenticated()  // 他のリクエストは認証を必要とする
            .requestMatchers("/edit").authenticated()  // 他のリクエストは認証を必要とする
            .requestMatchers("/delete").authenticated()  // 他のリクエストは認証を必要とする
            .anyRequest().permitAll()  // 認証なしでアクセス可能なURLパターン
        )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* CORS設定 */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Constants.ALLOWED_ORIGINS);
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // Secure属性をオフにするための設定（HTTPS環境でない場合は適用）
        configuration.addExposedHeader("Set-Cookie");


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
