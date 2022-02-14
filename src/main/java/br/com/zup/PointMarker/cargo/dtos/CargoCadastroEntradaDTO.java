package br.com.zup.PointMarker.cargo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CargoCadastroEntradaDTO {

    @NotBlank(message = "{nome.null}")
    @Size(min = 3, max = 30, message = "{nome.cargo.size}")
    private String nome;
    @Min(value = 700, message = "{salario.min}")
    private double salario;
    @Min(value = 4, message = "{cargahoraria.min}")
    @Max(value = 8,message = "{cargahoraria.max}")
    private int cargaHoraria;
}
