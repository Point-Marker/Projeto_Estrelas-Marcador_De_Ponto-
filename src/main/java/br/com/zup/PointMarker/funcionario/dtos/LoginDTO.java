package br.com.zup.PointMarker.funcionario.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {

    private String nomeUsuario;
    private String senha;
}
