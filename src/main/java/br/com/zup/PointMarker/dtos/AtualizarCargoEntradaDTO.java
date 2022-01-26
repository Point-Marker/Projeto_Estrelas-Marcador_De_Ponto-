package br.com.zup.PointMarker.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarCargoEntradaDTO {

    private Cargo cargo;
}
