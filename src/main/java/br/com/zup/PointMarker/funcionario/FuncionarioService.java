package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.dtos.AtualizarSalarioSaidaDTO;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario buscarFuncionario(int id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }
        return optionalFuncionario.get();
    }

    public Funcionario atualizarStatus(int id, double salario) {

        Funcionario funcionario = buscarFuncionario(id);

        if (funcionario != null) {
            funcionario.setSalario(salario);
        }
        return funcionario;
    }

    public Funcionario atualizarCargo(int idFuncionario, Cargo idCargo) {
        Funcionario funcionario = buscarFuncionario(idFuncionario);
        funcionario.setCargo(idCargo);

        return funcionario;
    }

    public Funcionario atualizarStatus(int id, Status status) {
        Funcionario funcionario = buscarFuncionario(id);
        funcionario.setStatus(status);

        return funcionario;
    }
}
