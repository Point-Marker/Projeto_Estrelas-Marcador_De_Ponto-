package br.com.zup.PointMarker.funcionario.dtos.AtualizarCargoDTO;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarCargoSaidaDTO {
    private Cargo cargo;
    private Status status;
    private String salario;
}
