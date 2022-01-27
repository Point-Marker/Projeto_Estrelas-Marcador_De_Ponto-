package br.com.zup.PointMarker.funcionario.dtos;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FuncionarioDTO {
    private int id;
    private String nome;
    private BancoDeHoras bancoDeHoras;

}
