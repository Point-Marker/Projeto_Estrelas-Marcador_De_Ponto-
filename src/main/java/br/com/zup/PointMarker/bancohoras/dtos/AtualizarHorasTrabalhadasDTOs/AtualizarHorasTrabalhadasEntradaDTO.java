package br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs;

import br.com.zup.PointMarker.funcionario.dtos.CpfDoFuncionarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarHorasTrabalhadasEntradaDTO {
    @Valid
    private CpfDoFuncionarioDTO funcionario;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime entrada;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime saida;
}
