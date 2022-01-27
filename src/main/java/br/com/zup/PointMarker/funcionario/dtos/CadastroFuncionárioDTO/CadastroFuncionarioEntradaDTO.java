package br.com.zup.PointMarker.funcionario.dtos.CadastroFuncionárioDTO;

import br.com.zup.PointMarker.cargo.Cargo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CadastroFuncionarioEntradaDTO {

    @NotBlank(message = "{nome.null}")
    @Size(min = 10, max = 30, message = "{nome.size}")
    private String nome;
    @CPF
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;
    private Cargo cargo;
}
