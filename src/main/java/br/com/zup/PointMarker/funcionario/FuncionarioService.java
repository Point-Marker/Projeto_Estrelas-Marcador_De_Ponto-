package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService (FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario buscarFuncionario (int id){
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }
        return optionalFuncionario.get();
    }
}
