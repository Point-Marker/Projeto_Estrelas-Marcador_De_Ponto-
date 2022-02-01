package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {

    List<Funcionario> findAllByStatus(Status status);

    Optional<Funcionario> findByNomeUsuario(String nomeUsuario);
}

