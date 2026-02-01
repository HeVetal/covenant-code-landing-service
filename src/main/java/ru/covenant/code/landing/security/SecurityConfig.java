package ru.covenant.code.landing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminsUserDetailsService adminsUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(adminsUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // ПУБЛИЧНЫЕ маршруты (доступны всем)
                        .requestMatchers(
                                "/",                              // Корневой путь
                                "/index.html",                    // Главная страница
                                "/v1/layout",                     // Лендинг
                                "/v1/registerForm",               // Форма заявки для клиентов
                                "/v1/admin/register",             // Форма регистрации админов - ПУБЛИЧНАЯ!
                                "/v1/login",                      // Страница входа
                                "/v1/success",                    // Страница успеха
                                "/styles.css",                    // CSS файлы
                                "/logo.svg",                      // Изображения
                                "/static/**",                     // Статические ресурсы
                                "/css/**",                        // CSS файлы
                                "/js/**",                         // JavaScript файлы
                                "/images/**",                     // Изображения
                                "/webjars/**",                    // WebJars
                                "/h2-console/**"                  // H2 Console
                        ).permitAll()

                        // ЗАКРЫТЫЕ маршруты (только для админов)
                        .requestMatchers("/admin/**").hasRole("ADMIN")           // Вся админка - только для админов!

                        // Все остальные маршруты требуют аутентификации
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/h2-console/**",
                                "/v1/registerForm",
                                "/v1/admin/register",
                                "/v1/login"
                        )
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authenticationProvider(authenticationProvider())
                .formLogin(form -> form
                        .loginPage("/v1/login")
                        .loginProcessingUrl("/v1/login")
                        .defaultSuccessUrl("/v1/layout", true)
                        .failureUrl("/v1/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/v1/logout")
                        .logoutSuccessUrl("/v1/layout?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .build();
    }
}