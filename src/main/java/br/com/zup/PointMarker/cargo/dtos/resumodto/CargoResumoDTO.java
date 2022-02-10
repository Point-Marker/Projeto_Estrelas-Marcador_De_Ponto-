package br.com.zup.PointMarker.cargo.dtos.resumodto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CargoResumoDTO {

    private String nome;
    private double salario;
    private int cargahoraria;
}
