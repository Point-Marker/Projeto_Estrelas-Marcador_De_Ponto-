package br.com.zup.PointMarker.cargo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {

    Optional<Cargo> findByNome(String nome);
}
