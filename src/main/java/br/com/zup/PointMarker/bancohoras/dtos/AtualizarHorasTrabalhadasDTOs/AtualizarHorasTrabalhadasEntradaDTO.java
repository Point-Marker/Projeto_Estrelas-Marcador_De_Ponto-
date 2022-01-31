package br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarHorasTrabalhadasEntradaDTO {

    private LocalTime entrada;
}
