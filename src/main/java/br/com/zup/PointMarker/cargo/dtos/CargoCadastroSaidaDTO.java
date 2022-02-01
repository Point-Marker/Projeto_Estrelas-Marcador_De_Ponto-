package br.com.zup.PointMarker.cargo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CargoCadastroSaidaDTO {

    private int id;
    private String nome;
    private double salario;
}
