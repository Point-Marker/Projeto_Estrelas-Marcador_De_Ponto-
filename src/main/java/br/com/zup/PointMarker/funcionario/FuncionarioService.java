package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoService;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.*;
import br.com.zup.PointMarker.usuario.Usuario;
import br.com.zup.PointMarker.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private CargoService cargoService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoService cargoService,
                              BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioRepository usuarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoService = cargoService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public Funcionario salvarFuncionario(Funcionario entradafuncionario) {

        Cargo cargo = cargoService.buscarCargo(entradafuncionario.getCargo().getId());
        entradafuncionario.setCargo(cargo);

        Optional<Usuario> usuarioOptional = usuarioRepository.findByNomeUsuario(entradafuncionario.getUsuario().getNomeUsuario());

        if (usuarioOptional.isEmpty()) {
            String senhaEscondida = bCryptPasswordEncoder.encode(entradafuncionario.getUsuario().getSenha());
            entradafuncionario.getUsuario().setSenha(senhaEscondida);
            entradafuncionario.setSalario(entradafuncionario.getCargo().getSalario());
            entradafuncionario.setStatus(Status.ATIVO);
            return funcionarioRepository.save(entradafuncionario);
        }

        throw new NomeDeUsuarioJaCadastrado();
    }

    public List<Funcionario> exibirTodosFuncionarios(Status status) {
        if (status != null) {
            if (status.equals(Status.ATIVO)) {
                return funcionarioRepository.findAllByStatus(status);
            } else if (status.equals(Status.INATIVO)) {
                return funcionarioRepository.findAllByStatus(status);
            }
            throw new StatusInvalidoException("Informe o status do funcion??rio como ATIVO ou INATIVO.");
        }

        return (List<Funcionario>) funcionarioRepository.findAll();
    }

    public Funcionario buscarFuncionario(int id) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Funcion??rio n??o encontrado.");
        }
        return optionalFuncionario.get();
    }

    public Funcionario atualizarSalario(int id, double salario) {

        Funcionario funcionario = buscarFuncionario(id);

        double tetoFuncionario = funcionario.getSalario() / 2;
        double tetoCargo = funcionario.getCargo().getSalario() / 2;

        if (funcionario.getStatus().equals(Status.ATIVO)) {
            if (salario < funcionario.getCargo().getSalario() &
                    funcionario.getSalario() == funcionario.getCargo().getSalario()) {

                if (salario > tetoFuncionario || salario > tetoCargo) {
                    throw new LimiteAumentoSalarioException("O aumento de sal??rio n??o pode ser maior ou menor do que 50% o sal??rio atual do " +
                            "funcion??rio ou do cargo do funcion??rio.");
                }

                funcionario.setSalario(salario + funcionario.getCargo().getSalario());

            } else if (salario < funcionario.getCargo().getSalario() &
                    funcionario.getSalario() != funcionario.getCargo().getSalario()) {

                if (salario > tetoFuncionario) {
                    throw new LimiteAumentoSalarioException("O aumento de sal??rio n??o pode ser maior ou menor do que 50% o sal??rio atual do " +
                            "funcion??rio ou do cargo do funcion??rio.");
                }

                funcionario.setSalario(funcionario.getSalario() + salario);

            } else if (funcionario.getSalario() <= funcionario.getCargo().getSalario()) {
                throw new AumentoDeSalarioInvalidoException("O aumento de salario n??o pode ser menor do que o sal??rio " +
                        "oferecido pelo cargo do Funcionario");
            }
            funcionarioRepository.save(funcionario);
            return funcionario;
        }
        throw new FuncionarioComStatusInativoException("Este Funcionario est?? INATIVO.");
    }

    public Funcionario atualizarCargo(int idFuncionario, Cargo idCargo) {
        Funcionario funcionario = buscarFuncionario(idFuncionario);

        if (funcionario.getStatus().equals(Status.ATIVO)) {
            Cargo cargo = cargoService.buscarCargo(idCargo.getId());

            funcionario.setCargo(cargo);
            funcionario.setSalario(cargo.getSalario());
            funcionarioRepository.save(funcionario);

            return funcionario;
        } else if (funcionario.getStatus().equals(Status.INATIVO)) {
            throw new FuncionarioComStatusInativoException("Este Funcionario est?? INATIVO.");
        }
        return funcionario;
    }

    public Funcionario atualizarStatus(int id, Status status) {
        Funcionario funcionario = buscarFuncionario(id);
        if (status.equals(Status.INATIVO)) {
            if (funcionario.getTotalHorasTrabalhadas() > 50) {
                throw new MaisDeCinquentaHorasTrabalhadasException("Este Funcionario Tem Mais de 50 Horas Trabalhadas Neste M??s");
            }
        }

        funcionario.setStatus(status);
        funcionarioRepository.save(funcionario);

        return funcionario;
    }

    public void deletarHorasTrabalhadas(int id) {
        Funcionario funcionario = buscarFuncionario(id);
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario buscarFuncionarioPeloCpf(String cpf) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findByCpf(cpf);

        if (optionalFuncionario.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Funcion??rio n??o encontrado.");
        }
        return optionalFuncionario.get();
    }
}
