package br.com.zup.PointMarker.funcionario.dtos.horasextras;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HorasExtrasDoFuncionarioDTO {
    private String nome;
    private String cpf;
    private int horasExtras;
}
