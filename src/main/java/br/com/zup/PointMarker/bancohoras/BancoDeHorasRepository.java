package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BancoDeHorasRepository extends CrudRepository<BancoDeHoras, Integer> {

    List<BancoDeHoras> findAllByFuncionario(Funcionario funcionario);
    BancoDeHoras findByDiaDoTrabalho(LocalDate diaDoTrabalho);
}
