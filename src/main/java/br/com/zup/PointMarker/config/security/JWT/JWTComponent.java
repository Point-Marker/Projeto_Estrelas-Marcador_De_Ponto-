package br.com.zup.PointMarker.config.security.JWT;

import br.com.zup.PointMarker.exceptions.TokenInvalidoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class JWTComponent {
    @Value("${jwt.segredo}")
    private String segredo;
    @Value("${jwt.milissegundos}")
    private Long milissegundos;

    public String gerarToken(String username, int id,
                             Collection<? extends GrantedAuthority> authentication) {
        Date vencimento = new Date(System.currentTimeMillis() + milissegundos);

        String token = Jwts.builder().setSubject(username)
                .claim("Autorizacao", authentication)
                .claim("idUsuario", id).setExpiration(vencimento)
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();
        return token;
    }

    public Claims pegarClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(segredo.getBytes())
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            throw new TokenInvalidoException();
        }
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = pegarClaims(token);
            Date dataAtual = new Date(System.currentTimeMillis());

            String username = claims.getSubject();
            Date vencimentoToken = claims.getExpiration();

            if (username != null && vencimentoToken != null && dataAtual.before(vencimentoToken)) {
                return true;
            } else {
                return false;
            }
        } catch (TokenInvalidoException e) {
            return false;
        }
    }
}
