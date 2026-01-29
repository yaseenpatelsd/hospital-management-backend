package hospital_management_sytem.Config;

import hospital_management_sytem.Enum.GlobalRole;
import hospital_management_sytem.Jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class UserConfi {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(auth -> auth

            // ✅ Always allow OPTIONS first
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // ✅ Swagger
            .requestMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**"
            ).permitAll()

            // ✅ PUBLIC AUTH / REGISTER endpoints
            .requestMatchers(
                "/patient/register",
                "/patient/account/verification",
                "/patient/otp/request/for/account/verification",
                "/patient/login",
                "/patient/request/otp",
                "/patient/password/reset",

                "/doctor/register",
                "/doctor/account/verification",
                "/doctor/login",
                "/doctor/request/otp",
                "/doctor/password/reset",

                "/staff/register",
                "/staff/account/verification",
                "/staff/login",
                "/staff/request/otp",
                "/staff/password/reset",

                "/admin/register",
                "/admin/login"
            ).permitAll()

            // 🔐 ROLE-BASED (put AFTER public endpoints)
            .requestMatchers("/patient/**").hasRole("PATIENT")
            .requestMatchers("/doctor/**").hasRole("DOCTOR")
            .requestMatchers("/staff/**").hasRole("STAFF")

            // 🔒 Everything else
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
  @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}




