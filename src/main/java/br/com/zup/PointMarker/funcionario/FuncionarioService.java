package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> exibirFuncionarios(Status status) {
        if (status != null){
            return funcionarioRepository.findAllByStatus(status);
        }

        return (List<Funcionario>) funcionarioRepository.findAll();
    }

}
