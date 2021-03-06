package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    private double salario;
    private LocalDate dataDeNascimento;
    @ManyToOne
    private Cargo cargo;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int totalHorasTrabalhadas;
    private int horasExtras;
    @OneToMany(mappedBy = "funcionario")
    private List<BancoDeHoras> bancoDeHoras;
    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
}
