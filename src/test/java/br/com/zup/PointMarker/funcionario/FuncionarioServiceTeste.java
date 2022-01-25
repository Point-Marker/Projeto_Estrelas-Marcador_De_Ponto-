package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@SpringBootTest
public class FuncionarioServiceTeste {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private Cargo cargo;


    @BeforeEach
    public void setUp() {

        cargo = new Cargo();

        cargo.setNome("Estagiario");
        cargo.setSalario(700);
        cargo.setId(1);

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso");
        funcionario.setCpf("159.307.330-58");
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);
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
}
