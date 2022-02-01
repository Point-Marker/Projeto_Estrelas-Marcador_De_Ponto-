package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class FuncionarioDetailsDTO {
    private int id;
    private String nome;
    private String cpf;
    private double salario;
    private LocalDate dataDeNascimento;
    private Cargo cargo;
    private Status status;
    private int totalHorasTrabalhadas;
    private int horasExtras;
}
