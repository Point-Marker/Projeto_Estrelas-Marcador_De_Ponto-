package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.cargo.CargoRepository;
import br.com.zup.PointMarker.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @MockBean
    private CargoRepository cargoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private Cargo cargo;
    private Status status;

    @BeforeEach
    public void setUp() {

        cargo = new Cargo();

        cargo.setNome("Estagiario");
        cargo.setSalario(700);
        cargo.setId(1);

        funcionario = new Funcionario();
        funcionario.setNome("Afonso");
        funcionario.setCpf("159.307.330-58");
//        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);
    }

    @Test
    public void testarCadastrarFuncionarioCaminhoBom() {
        Mockito.when(cargoRepository.findById(1)).thenReturn(Optional.of(cargo));
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

}
