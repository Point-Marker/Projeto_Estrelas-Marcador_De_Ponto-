package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "banco_horas")
public class BancoDeHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Funcionario funcionario;
    private LocalDate diaDoTrabalho;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime entrada;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime saida;

}
