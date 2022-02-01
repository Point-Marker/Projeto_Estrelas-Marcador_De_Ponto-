package br.com.zup.PointMarker.cargo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cargos")
public class Cargo implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = true)
    private String nome;
    @Column(nullable = true)
    private double salario;
    private int cargoHoraria;

    @Override
    public String getAuthority() {
        return nome;
    }

    @Override
    public String toString() {
        return "Cargo [id= " + id + ", nome= " + nome + "]";
    }
}
