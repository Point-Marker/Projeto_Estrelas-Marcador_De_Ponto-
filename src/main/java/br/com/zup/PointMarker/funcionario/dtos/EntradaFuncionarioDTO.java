package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EntradaFuncionarioDTO {
    private String nome;
    private String cpf;
    private double salario;
    private LocalDate dataDeNascimento;
    private Cargo cargo;
}
