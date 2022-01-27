package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResumoFuncionarioDTO {
    private String nome;
    private String cpf;
    private Cargo cargo;
}
