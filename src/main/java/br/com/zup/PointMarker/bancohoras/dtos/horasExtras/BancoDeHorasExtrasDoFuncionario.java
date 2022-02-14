package br.com.zup.PointMarker.bancohoras.dtos.horasExtras;

import br.com.zup.PointMarker.funcionario.dtos.horasextras.HorasExtrasDoFuncionarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class BancoDeHorasExtrasDoFuncionario {
    private HorasExtrasDoFuncionarioDTO funcionario;
    private LocalDate diaDoTrabalho;
    private LocalTime entrada;
    private LocalTime saida;


}
