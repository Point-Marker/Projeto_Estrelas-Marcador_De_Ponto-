package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarStatusEntradaDTO {
    private Status status;
}
