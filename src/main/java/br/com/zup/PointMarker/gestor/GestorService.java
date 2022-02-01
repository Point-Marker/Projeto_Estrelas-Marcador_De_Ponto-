package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioRepository;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorService {

    private BancoDeHorasService bancoDeHorasService;
    private FuncionarioService funcionarioService;
    private FuncionarioRepository funcionarioRepository;
    private GestorRepository gestorRepository;

    @Autowired
    public GestorService(BancoDeHorasService bancoDeHorasService, FuncionarioService funcionarioService,
                         FuncionarioRepository funcionarioRepository, GestorRepository gestorRepository) {
        this.bancoDeHorasService = bancoDeHorasService;
        this.funcionarioService = funcionarioService;
        this.funcionarioRepository = funcionarioRepository;
        this.gestorRepository = gestorRepository;
    }

    public Funcionario cadastrarFuncionario(Funcionario entradaFuncionario) {
        Funcionario funcionario = funcionarioService.salvarFuncionario(entradaFuncionario);

        return funcionario;
    }

    public List<Funcionario> exibirTodosFuncionarios(Status status) {
        List<Funcionario> todosFuncionario = funcionarioService.exibirTodosFuncionarios(status);

        return todosFuncionario;
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

    public List<BancoDeHoras> exibirTodosBancosDeHoras() {
        List<BancoDeHoras> todosBancosDeHoras = bancoDeHorasService.exibirTodosBancosDeHoras();
        return todosBancosDeHoras;
    }
}