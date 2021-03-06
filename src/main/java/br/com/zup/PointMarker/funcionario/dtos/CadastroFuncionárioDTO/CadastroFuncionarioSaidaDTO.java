package br.com.zup.PointMarker.funcionario.dtos.CadastroFuncion√°rioDTO;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class CadastroFuncionarioSaidaDTO {
    private String nome;
    private String cpf;
    private double salario;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;
    private Cargo cargo;
    private Status status;
    private Usuario usuario;
}
