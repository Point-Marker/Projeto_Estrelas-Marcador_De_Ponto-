package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EntradaFuncionarioDTO {
    @NotBlank(message = "{nome.null}")
    private String nome;
    @CPF
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;
    private Cargo cargo;
}
