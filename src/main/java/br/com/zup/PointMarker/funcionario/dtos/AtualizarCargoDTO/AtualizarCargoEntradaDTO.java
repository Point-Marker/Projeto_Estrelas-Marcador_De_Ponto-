package br.com.zup.PointMarker.funcionario.dtos.AtualizarCargoDTO;

import br.com.zup.PointMarker.cargo.Cargo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarCargoEntradaDTO {

    @NotNull
    private Cargo cargo;
}
