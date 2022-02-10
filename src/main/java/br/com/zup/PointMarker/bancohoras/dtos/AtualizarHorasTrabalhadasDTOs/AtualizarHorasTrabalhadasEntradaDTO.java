package br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class AtualizarHorasTrabalhadasEntradaDTO {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime entrada;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime saida;
}
