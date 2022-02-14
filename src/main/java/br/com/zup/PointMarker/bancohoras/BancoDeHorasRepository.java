package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BancoDeHorasRepository extends CrudRepository<BancoDeHoras, Integer> {

    List<BancoDeHoras> findAllByFuncionario(Funcionario funcionario);

    BancoDeHoras findByDiaDoTrabalho(LocalDate diaDoTrabalho);

    List<BancoDeHoras> findAllByDiaDoTrabalho(LocalDate mesDeFiltro);

    Optional<BancoDeHoras> findByDiaDoTrabalhoAndFuncionario(LocalDate diaDoTrabalho, Funcionario funcionario);
}
