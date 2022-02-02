package br.com.zup.PointMarker.config.security.JWT;

import br.com.zup.PointMarker.config.security.UsuarioLogado;
import br.com.zup.PointMarker.config.security.UsuarioLogadoService;
import br.com.zup.PointMarker.exceptions.AcessoNegadoException;
import br.com.zup.PointMarker.funcionario.dtos.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class FiltroDeAutenticacaoJWT extends UsernamePasswordAuthenticationFilter {
    private JWTComponent jwtComponent;
    private AuthenticationManager authenticationManager;
    private UsuarioLogadoService usuarioLogadoService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            LoginDTO login = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
            UserDetails userDetails = usuarioLogadoService.loadUserByUsername(login.getNomeUsuario());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    login.getNomeUsuario(), login.getSenha(), userDetails.getAuthorities());
            Authentication autenticacao = authenticationManager.authenticate(authToken);
            return autenticacao;
        } catch (IOException e) {
            throw new AcessoNegadoException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UsuarioLogado usuarioLogado = (UsuarioLogado) authResult.getPrincipal();
        String username = usuarioLogado.getUsername();
        int id = usuarioLogado.getId();

        String token = jwtComponent.gerarToken(username, id);

        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Authorization", "Token " + token);
    }
}
