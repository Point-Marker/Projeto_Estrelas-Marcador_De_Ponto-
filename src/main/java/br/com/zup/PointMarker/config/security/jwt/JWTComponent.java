package br.com.zup.PointMarker.config.security.jwt;

import org.springframework.beans.factory.annotation.Value;

public class JWTComponent {
    @Value("{jwt.segredo}")
    private String segredo;
    @Value("{jwt.tempo}")
    private Long milissegundo;
}
