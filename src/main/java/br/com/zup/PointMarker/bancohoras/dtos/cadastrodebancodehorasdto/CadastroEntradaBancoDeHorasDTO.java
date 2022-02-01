package br.com.zup.PointMarker.bancohoras.dtos.cadastrodebancodehorasdto;

import br.com.zup.PointMarker.funcionario.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class CadastroEntradaBancoDeHorasDTO {
    private Funcionario funcionario;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime entrada;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime saida;
}
