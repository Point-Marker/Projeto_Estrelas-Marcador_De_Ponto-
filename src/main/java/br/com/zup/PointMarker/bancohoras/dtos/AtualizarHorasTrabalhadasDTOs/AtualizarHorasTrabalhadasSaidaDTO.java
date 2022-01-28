package br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs;

import br.com.zup.PointMarker.funcionario.Funcionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarHorasTrabalhadasSaidaDTO {

    private Funcionario id_funcionario;
    private LocalDateTime entrada;
    private LocalDateTime saida;
}
