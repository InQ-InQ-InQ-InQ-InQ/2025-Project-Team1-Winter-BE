package club.inq.team1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/login","/loginProcess","/swagger-ui/**").permitAll()
                .requestMatchers("/user").hasRole("USER")
                .anyRequest().permitAll()
        );

        http.formLogin(login->login
                .loginPage("/login")
                .loginProcessingUrl("/loginProcess")
        );

        http.logout(logout->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
