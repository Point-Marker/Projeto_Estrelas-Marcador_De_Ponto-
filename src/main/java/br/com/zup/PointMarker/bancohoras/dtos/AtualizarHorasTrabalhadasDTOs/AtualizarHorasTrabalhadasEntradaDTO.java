package br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs;

import br.com.zup.PointMarker.funcionario.dtos.CpfDoFuncionarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarHorasTrabalhadasEntradaDTO {
    private int id;
    @Valid
    private CpfDoFuncionarioDTO funcionario;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate diaDoTrabalho;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime entrada;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime saida;
}
