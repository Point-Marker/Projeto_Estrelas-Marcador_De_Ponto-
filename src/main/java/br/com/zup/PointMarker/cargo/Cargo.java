package br.com.zup.PointMarker.cargo;

import br.com.zup.PointMarker.funcionario.Funcionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cargos")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = true)
    private String nome;
    @Column(nullable = true)
    private double salario;
    private int cargoHoraria;
}
