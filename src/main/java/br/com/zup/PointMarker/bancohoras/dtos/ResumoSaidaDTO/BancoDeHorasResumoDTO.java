package br.com.zup.PointMarker.bancohoras.dtos.ResumoDTO;

import br.com.zup.PointMarker.funcionario.dtos.ResumoDTO.ResumoFuncionarioDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BancoDeHorasResumoDTO {
    private ResumoFuncionarioDTO funcionario;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime entrada;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime saida;
}
