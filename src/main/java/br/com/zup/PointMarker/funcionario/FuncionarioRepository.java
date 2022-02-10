package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.usuario.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

    List<Funcionario> findAllByStatus(Status status);

    Usuario findByUsuario(String nomeUsuario);

    Optional<Funcionario> findByCpf(String cpf);
}

