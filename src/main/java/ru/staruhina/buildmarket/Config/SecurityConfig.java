package ru.staruhina.buildmarket.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests() //Объект http
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/home", "/products/**", "/style/**", "/").permitAll() //permitAll кто угодно
                .requestMatchers("/auth/login", "/auth/register").permitAll()// authenticated что доступно ауинтифицированным
                .requestMatchers("/user/**", "/order/**").authenticated()
                .and()
                .formLogin().loginPage("/auth/login") // Есть страница авторизация login => это URL страницы для авторизации
                .loginProcessingUrl("/process_login") // Постформа сюда адресуется
                .defaultSuccessUrl("/", true) // URL после удачной авторизации
                .failureUrl("/auth/login?error") // URL возрат при ошибке авторизации
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/"); // logout URL перенаправление
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       UserDetailsService userDetailsService, // Класс отвечающий за информацию за пользователя, роли, активы и т.д.
                                                       PasswordEncoder encoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder).and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
