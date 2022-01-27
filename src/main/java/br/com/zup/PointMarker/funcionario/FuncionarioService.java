package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoRepository;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.FuncionarioComStatusInativo;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        cargoOptional.orElseThrow(NoSuchElementException::new);

        entradafuncionario.setSalario(entradafuncionario.getCargo().getSalario());
        entradafuncionario.setStatus(Status.ATIVO);
        return funcionarioRepository.save(entradafuncionario);
    }

    public List<Funcionario> exibirFuncionarios(Status status) {
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

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        optionalFuncionario.orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado."));

        Funcionario funcionario = optionalFuncionario.get();
        funcionario.setSalario(salario);

        funcionarioRepository.save(funcionario);

        return funcionario;
    }

    public Funcionario atualizarCargo(int idFuncionario, Cargo idCargo) {
        Funcionario funcionario = buscarFuncionario(idFuncionario);

        if (funcionario != null) {
            if (funcionario.getStatus() != Status.INATIVO) {

                Optional<Cargo> cargoOptional = cargoRepository.findById(idCargo.getId());
                funcionario.setCargo(cargoOptional.get());
                funcionario.setSalario(cargoOptional.get().getSalario());
                funcionarioRepository.save(funcionario);
            }
            throw new FuncionarioComStatusInativo("Este Funcionario Não Pode Ter O Seu Cargo Alterado, pois ele está INATIVO.");
        }
        return funcionario;
    }

    public Funcionario atualizarStatus(int id, Status status) {

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        optionalFuncionario.orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado."));

        Funcionario funcionario = optionalFuncionario.get();
        funcionario.setStatus(status);

        funcionarioRepository.save(funcionario);

        return funcionario;
    }

}
