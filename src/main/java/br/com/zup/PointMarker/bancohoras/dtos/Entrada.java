package br.com.zup.PointMarker.bancohoras.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Entrada {
    private LocalDate diaEntrada;
    private Time hora;
}
