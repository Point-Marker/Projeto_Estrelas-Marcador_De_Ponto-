package br.com.zup.PointMarker.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;

public class AtualizarSalarioSaidaDTO {
    private Cargo cargo;
    private Status status;
    private String salario;
}