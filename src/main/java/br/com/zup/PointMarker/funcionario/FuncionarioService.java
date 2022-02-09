package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoRepository;
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
    private CargoRepository cargoRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
                              BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioRepository usuarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public Funcionario salvarFuncionario(Funcionario entradafuncionario) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(entradafuncionario.getCargo().getId());
        cargoOptional.orElseThrow();
        entradafuncionario.setCargo(cargoOptional.get());

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
            throw new StatusInvalidoException("Informe o status do funcionário como ATIVO ou INATIVO.");
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

        double tetoFuncionario = funcionario.getSalario() / 2;
        double tetoCargo = funcionario.getCargo().getSalario() / 2;

        if (funcionario.getStatus().equals(Status.ATIVO)) {
            if (salario < funcionario.getCargo().getSalario() &
                    funcionario.getSalario() == funcionario.getCargo().getSalario()) {

                if (salario > tetoFuncionario || salario > tetoCargo) {
                    throw new LimiteAumentoSalarioException("O aumento de salário não pode ser maior ou menor do que 50% o salário atual do " +
                            "funcionário ou do cargo do funcionário.");
                }

                funcionario.setSalario(salario + funcionario.getCargo().getSalario());

            } else if (salario < funcionario.getCargo().getSalario() &
                    funcionario.getSalario() != funcionario.getCargo().getSalario()) {

                if (salario > tetoFuncionario) {
                    throw new LimiteAumentoSalarioException("O aumento de salário não pode ser maior ou menor do que 50% o salário atual do " +
                            "funcionário ou do cargo do funcionário.");
                }

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
        Funcionario funcionario = buscarFuncionario(id);
        if (status.equals(Status.INATIVO)) {
            if (funcionario.getTotalHorasTrabalhadas() > 50) {
                throw new MaisDeCinquentaHorasTrabalhadasException("Este Funcionario Tem Mais de 50 Horas Trabalhadas Neste Mês");
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

}
