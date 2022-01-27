package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @OneToOne
    private Funcionario id_funcionario;
    private int horaExtra;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime entrada;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime saida;
}
// Hora extra vai ser do tipo int. Quando hora extra estiver com o número 0, o funcionario
// Não tem hora extra, caso o funcionario coloque 1 na horaextra, ele colocará somente os valores de hora extra.
//Ou seja, é possível fazer dois cadastros em um mesmo dia.
// Todo cargo tem a sua cargo horária. Se passar Desta Cargo_Horaria, será considerado hora extra.