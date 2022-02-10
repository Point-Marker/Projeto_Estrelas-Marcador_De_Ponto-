package br.com.zup.PointMarker.funcionario.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@Getter
@Setter
public class CpfDoFuncionarioDTO {
    @CPF
    private String cpf;
}
