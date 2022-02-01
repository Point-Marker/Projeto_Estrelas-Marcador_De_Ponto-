package br.com.zup.PointMarker.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

    private static final String[] END_POINTS = {
            "/funcionario",

    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(configurarCors());

        http.authorizeRequests().antMatchers(HttpMethod.POST, END_POINTS).permitAll()
                .anyRequest().hasRole("gestor");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource configurarCors() {
        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return cors;
    }

}
