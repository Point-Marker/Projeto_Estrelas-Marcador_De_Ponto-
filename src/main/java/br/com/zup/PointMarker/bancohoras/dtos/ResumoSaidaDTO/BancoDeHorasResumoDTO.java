package br.com.zup.PointMarker.bancohoras.dtos.ResumoSaidaDTO;

import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.ResumoDTO.ResumoFuncionarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class BancoDeHorasResumoDTO {
    private int id;
    private ResumoFuncionarioDTO funcionario;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate diaDoTrabalho;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime entrada;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime saida;

}
