package br.com.zup.PointMarker.config.security;

import br.com.zup.PointMarker.config.security.JWT.FiltroDeAutenticacaoJWT;
import br.com.zup.PointMarker.config.security.JWT.FiltroDeAutorizacao;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTComponent jwtComponent;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] END_POINTS_PUBLICO = {
            "/dashboard/cadastro/funcionarios",

    };

    private static final String[] END_POINTS_POST = {
            "/bancohoras",
    };

    private static final String[] END_POINTS_GET = {
            "/dashboard/{id}",
            "/funcionario/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(configurarCors());

        http.authorizeRequests().antMatchers(HttpMethod.POST, END_POINTS_PUBLICO).permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, END_POINTS_POST).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, END_POINTS_GET).hasAnyAuthority("USER","ADMIN")
                .and().authorizeRequests().anyRequest().hasAuthority("ADMIN");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new FiltroDeAutenticacaoJWT(jwtComponent, authenticationManager()));
        http.addFilter(new FiltroDeAutorizacao(authenticationManager(), jwtComponent, userDetailsService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
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
