package br.com.zup.PointMarker.usuario;

import br.com.zup.PointMarker.funcionario.Funcionario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {

    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
