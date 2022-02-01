package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioRepository;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorService {

    private FuncionarioService funcionarioService;
    private FuncionarioRepository funcionarioRepository;
    private GestorRepository gestorRepository;

    @Autowired
    public GestorService(FuncionarioService funcionarioService, GestorRepository gestorRepository, FuncionarioRepository funcionarioRepository) {
        this.funcionarioService = funcionarioService;
        this.funcionarioRepository = funcionarioRepository;
        this.gestorRepository = gestorRepository;
    }

    public List<Funcionario> exibirTodosFuncionarios(Status status) {
        List<Funcionario> funcionario = funcionarioService.exibirTodosFuncionarios(status);

        return funcionario;
    }

    public Funcionario cadastrarFuncionario(Funcionario entradaFuncionario) {
        Funcionario funcionario = funcionarioService.salvarFuncionario(entradaFuncionario);

        return funcionario;
    }

    public void removerFuncionario(int id) {
        funcionarioService.deletarHorasTrabalhadas(id);
    }
}
