package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.bancohoras.dtos.DetailsDTO.BancoDeHorasDetailsDTO;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FuncionarioDetailsDTO {
    private int id;
    private String nome;
    private String cpf;
    private double salario;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;
    private Cargo cargo;
    private Status status;
    private int totalHorasTrabalhadas;
    private int horasExtras;
    private List<BancoDeHorasDetailsDTO> bancoDeHorasDetailsDTO;
}
