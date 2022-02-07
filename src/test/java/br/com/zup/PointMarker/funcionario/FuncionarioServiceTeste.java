package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoRepository;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.*;
import br.com.zup.PointMarker.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTeste {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private FuncionarioService funcionarioService;


    private Funcionario funcionario;
    private Cargo cargo;
    private Status status;
    private Usuario usuario;


    @BeforeEach
    public void setUp() {

        cargo = new Cargo();

        cargo.setNome("Estagiario");
        cargo.setSalario(700);
        cargo.setId(1);

        usuario = new Usuario();
        usuario.setNomeUsuario("Afonso");
        usuario.setSenha("1234");

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso Benedito de Souza   ");
        funcionario.setCpf("159.307.330-58");
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);

        funcionario.setUsuario(usuario);
    }

    @Test
    public void testarCadastroDeFuncionarioCaminhoBom() {
        Mockito.when(cargoRepository.findById(1)).thenReturn(Optional.of(cargo));
        Mockito.when(bCryptPasswordEncoder.encode(funcionario.getUsuario().getSenha())).thenReturn("senhaCripto");
        Mockito.when(funcionarioRepository.save(Mockito.any(Funcionario.class))).thenReturn(funcionario);

        Funcionario funcionarioCadastrado = funcionarioService.salvarFuncionario(funcionario);

        Mockito.verify(funcionarioRepository, Mockito.times(1)).save(funcionario);
        Assertions.assertEquals(funcionarioCadastrado, funcionario);
    }

    @Test
    public void testarCadastroDeFuncionarioCaminhoRuim_CargoNaoEncontrado() {
        var cargoNaoCadastrado = new Cargo();
        cargoNaoCadastrado.setId(2);

        funcionario.setCargo(cargoNaoCadastrado);

        Mockito.when(cargoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            funcionarioService.salvarFuncionario(funcionario);
        });

        Mockito.verify(funcionarioRepository, Mockito.times(0)).save(funcionario);

    }

    @Test
    public void buscarFuncionarioCaminhoVerdadeiro() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));

        Funcionario funcionarioEncontrado = funcionarioService.buscarFuncionario(1);

        Assertions.assertEquals(funcionarioEncontrado, funcionario);
    }

    @Test
    public void buscarFuncionarioCaminhoFalso() {
        Mockito.when(funcionarioRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertThrows(FuncionarioNaoEncontradoException.class, () -> {
            funcionarioService.buscarFuncionario(2);
        });
    }

    @Test
    public void atualizarSalarioCaminhoVerdadeiro() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
        funcionario.setSalario(1000);
        Funcionario funcionario = funcionarioService.atualizarSalario(1, 350);

        Assertions.assertEquals(funcionario.getSalario(), 1350);
    }

    @Test
    public void atualizarSalarioCaminhoFalso_quandoSalarioForMaiorQueTetoSalarialDoCargo() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
        cargo.setSalario(800);
        funcionario.setSalario(1000);

        Assertions.assertThrows(LimiteAumentoSalarioException.class, () -> funcionarioService.atualizarSalario(1, 700));
    }

    @Test
    public void atualizarSalarioCaminhoFalso_quandoSalarioForMaiorQueTetoSalarialDoFuncionario() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
        cargo.setSalario(1000);
        funcionario.setSalario(800);

        Assertions.assertThrows(LimiteAumentoSalarioException.class, () -> funcionarioService.atualizarSalario(1, 700));
    }

    /*@Test
    public void atualizarSalarioCaminhoFalso_quandoSalarioDeFuncionarioForMenorQueSalarialDoCargo() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
        cargo.setSalario(1000);
        funcionario.setSalario(100);

        Assertions.assertThrows(AumentoDeSalarioInvalidoException.class, () -> funcionarioService.atualizarSalario(1, 100));
    }*/

    @Test
    public void atualizarCargoCaminhoPositivo() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
        Mockito.when(cargoRepository.findById(1)).thenReturn(Optional.ofNullable(cargo));
        Cargo cargo = funcionario.getCargo();

        Funcionario funcionario = funcionarioService.atualizarCargo(1, cargo);

        Assertions.assertNotNull(funcionario);
    }

    @Test
    public void atualizarcargoCaminhoNegativo() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
       funcionario.setStatus(Status.INATIVO);

        Assertions.assertThrows(FuncionarioComStatusInativoException.class, () ->
                funcionarioService.atualizarCargo(1, cargo));
    }

    @Test
    public void atualizarStatusCaminhoVerdadeiro() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));

        Funcionario funcionario = funcionarioService.atualizarStatus(1, Status.ATIVO);

        Assertions.assertEquals(funcionario.getStatus(), Status.ATIVO);
    }

    @Test
    public void atualizarStatusCaminhoFalso() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.ofNullable(funcionario));
        status = Status.INATIVO;
        funcionario.setTotalHorasTrabalhadas(60);

        Assertions.assertThrows(MaisDeCinquentaHorasTrabalhadasException.class, () ->
                funcionarioService.atualizarStatus(1, status));
    }

    @Test
    public void testarExibicaoDeFuncionarios_quandoStatusForNulo() {
        status = null;
        Mockito.when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));

        List<Funcionario> funcionariosExibidos = funcionarioService.exibirTodosFuncionarios(status);
        for (Funcionario funcionarioReferencia : funcionariosExibidos) {
            Assertions.assertEquals(funcionarioReferencia, funcionario);
        }
        Mockito.verify(funcionarioRepository, Mockito.times(0)).findAllByStatus(status);
    }

    @Test
    public void testarExibicaDeFuncionarios_quandoStatusNaoForNulo() {
        status = Status.ATIVO;
        Mockito.when(funcionarioRepository.findAllByStatus(status)).thenReturn(List.of(funcionario));

        List<Funcionario> funcionariosAtivos = funcionarioService.exibirTodosFuncionarios(status);
        for (Funcionario funcionarioReferencia : funcionariosAtivos) {
            Assertions.assertEquals(funcionarioReferencia.getStatus(), Status.ATIVO);
        }
        Mockito.verify(funcionarioRepository, Mockito.times(0)).findAll();
    }

    @Test
    public void testarDeletarHorasTrabalhadas() {
        Mockito.when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));
        funcionarioService.deletarHorasTrabalhadas(funcionario.getId());

        Mockito.verify(funcionarioRepository).delete(funcionario);
    }

}
