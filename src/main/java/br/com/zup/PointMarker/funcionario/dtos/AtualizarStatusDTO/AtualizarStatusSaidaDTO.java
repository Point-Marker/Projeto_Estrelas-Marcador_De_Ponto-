
package br.com.zup.PointMarker.funcionario.dtos.AtualizarStatusDTO;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarStatusSaidaDTO {
    private int id;
    private String nome;
    private Cargo cargo;
    private Status status;
}
