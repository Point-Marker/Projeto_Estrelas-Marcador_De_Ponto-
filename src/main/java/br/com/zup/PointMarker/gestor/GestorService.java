package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoRepository;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.CargoJaCadastradoException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestorService {

    private FuncionarioService funcionarioService;
    private CargoRepository cargoRepository;

    @Autowired
    public GestorService(FuncionarioService funcionarioService, CargoRepository cargoRepository) {
        this.funcionarioService = funcionarioService;
        this.cargoRepository = cargoRepository;
    }

    public Funcionario cadastrarFuncionario(Funcionario entradaFuncionario) {
        Funcionario funcionario = funcionarioService.salvarFuncionario(entradaFuncionario);

        return funcionario;
    }

    public List<Funcionario> exibirTodosFuncionarios(Status status) {
        return funcionarioService.exibirTodosFuncionarios(status);
    }

    public Funcionario exibirUmFuncionario(int id) {
        Funcionario umFuncionario = funcionarioService.buscarFuncionario(id);

        return umFuncionario;
    }

    public void removerFuncionario(int id) {
        funcionarioService.deletarHorasTrabalhadas(id);
    }

    public Funcionario atualizarSalario(int id, double salario) {
        Funcionario salarioFuncionario = funcionarioService.atualizarSalario(id, salario);

        return salarioFuncionario;
    }

    public Funcionario atualizarCargo(int idFuncionario, Cargo idCargo) {
        Funcionario cargoFuncionario = funcionarioService.atualizarCargo(idFuncionario, idCargo);

        return cargoFuncionario;
    }

    public Funcionario atualizarStatus(int id, Status status) {
        Funcionario statusFuncionario = funcionarioService.atualizarStatus(id, status);

        return statusFuncionario;
    }

    public Cargo cadastrarCargo(Cargo cargo) {
        Optional<Cargo> cargoOptional = cargoRepository.findByNome(cargo.getNome());

        if (cargoOptional.isEmpty()) {
            return cargoRepository.save(cargo);
        }
        throw new CargoJaCadastradoException("Cargo já cadastrado.");
    }

}
