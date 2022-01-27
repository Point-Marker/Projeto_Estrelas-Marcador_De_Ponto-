package br.com.zup.PointMarker.dtos;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;

public class AtualizarStatusSaidaDTO {
    private int id;
    private String nome;
    private Cargo cargo;
    private Status status;
}
