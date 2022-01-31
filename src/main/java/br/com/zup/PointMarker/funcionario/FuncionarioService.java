package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoRepository;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.AumentoDeSalarioInvalidoException;
import br.com.zup.PointMarker.exceptions.FuncionarioComStatusInativoException;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private CargoRepository cargoRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public Funcionario salvarFuncionario(Funcionario entradafuncionario) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(entradafuncionario.getCargo().getId());
        entradafuncionario.setCargo(cargoOptional.get());
        cargoOptional.orElseThrow();

        entradafuncionario.setSalario(entradafuncionario.getCargo().getSalario());
        entradafuncionario.setStatus(Status.ATIVO);
        return funcionarioRepository.save(entradafuncionario);
    }

    public List<Funcionario> exibirTodosFuncionarios(Status status) {
        if (status != null) {
            return funcionarioRepository.findAllByStatus(status);
        }

        return (List<Funcionario>) funcionarioRepository.findAll();
    }

    public Funcionario buscarFuncionario(int id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }
        return optionalFuncionario.get();
    }

    public Funcionario atualizarSalario(int id, double salario) {

        Funcionario funcionario = buscarFuncionario(id);
        if (funcionario.getStatus().equals(Status.ATIVO)) {
            if (salario < funcionario.getCargo().getSalario() &
                    funcionario.getSalario() == funcionario.getCargo().getSalario()) {

                funcionario.setSalario(salario + funcionario.getCargo().getSalario());

            } else if (salario < funcionario.getCargo().getSalario() &
                    funcionario.getSalario() != funcionario.getCargo().getSalario()) {

                funcionario.setSalario(funcionario.getSalario() + salario);

            } else if (funcionario.getSalario() <= funcionario.getCargo().getSalario()) {
                throw new AumentoDeSalarioInvalidoException("O aumento de salario não pode ser menor do que o salário " +
                        "oferecido pelo cargo do Funcionario");
            }
            funcionarioRepository.save(funcionario);
            return funcionario;
        }
        throw new FuncionarioComStatusInativoException("Este Funcionario está INATIVO.");
    }

    public Funcionario atualizarCargo(int idFuncionario, Cargo idCargo) {
        Funcionario funcionario = buscarFuncionario(idFuncionario);

        if (funcionario.getStatus().equals(Status.ATIVO)) {

            Optional<Cargo> cargoOptional = cargoRepository.findById(idCargo.getId());
            funcionario.setCargo(cargoOptional.get());
            funcionario.setSalario(cargoOptional.get().getSalario());
            funcionarioRepository.save(funcionario);
            return funcionario;
        }
        throw new FuncionarioComStatusInativoException("Este Funcionario está INATIVO.");

    }

    public Funcionario atualizarStatus(int id, Status status) {

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        optionalFuncionario.orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado."));

        Funcionario funcionario = buscarFuncionario(id);
        if (status.equals(Status.INATIVO)) {
            if (funcionario.getTotalHorasTrabalhadas() > 50) {
                throw new RuntimeException("Este Funcionario Tem Mais de 50 Horas Trabalhadas Neste Mês");
            }
        }

        funcionario.setStatus(status);
        funcionarioRepository.save(funcionario);

        return funcionario;
    }

    public void deletarHorasTrabalhadas(int id) {
        Funcionario funcionario = buscarFuncionario(id);
        funcionario.setTotalHorasTrabalhadas(0);
        funcionarioRepository.save(funcionario);
    }

}
