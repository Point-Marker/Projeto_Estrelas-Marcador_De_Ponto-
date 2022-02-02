package br.com.zup.PointMarker.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTComponent {
    @Value("{jwt.segredo}")
    private String segredo;
    @Value("{jwt.tempo}")
    private Long milissegundo;

    public String gerarToken(String username, int id){
        Date vencimento = new Date(System.currentTimeMillis()*milissegundo);

        String token = Jwts.builder().setSubject(username)
                .claim("idUsuario", id).setExpiration(vencimento)
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();
        return token;
    }

}
