package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "banco_horas")
public class BancoDeHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime entrada;
    private LocalDateTime saida;
}
