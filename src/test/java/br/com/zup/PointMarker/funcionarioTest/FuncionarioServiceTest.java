package br.com.zup.PointMarker.funcionarioTest;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioRepository;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootTest
public class FuncionarioServiceTest {

    @Autowired
    private FuncionarioService funcionarioService;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

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
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);
    }

    @Test
    public void testarExibicaoDeFuncionarios_quandoStatusForNulo() {
        status = null;
        Mockito.when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));

        List<Funcionario> funcionariosExibidos = funcionarioService.exibirFuncionarios(status);
        for (Funcionario funcionarioReferencia : funcionariosExibidos) {
            Assertions.assertEquals(funcionarioReferencia, funcionario);
        }
        Mockito.verify(funcionarioRepository, Mockito.times(0)).findAllByStatus(status);
    }

    @Test
    public void testarExibicaDeFuncionarios_quandoStatusNaoForNulo(){
        status = Status.ATIVO;
        Mockito.when(funcionarioRepository.findAllByStatus(status)).thenReturn(List.of(funcionario));

        List<Funcionario> funcionariosAtivos = funcionarioService.exibirFuncionarios(status);
        for (Funcionario funcionarioReferencia: funcionariosAtivos) {
                Assertions.assertEquals(funcionarioReferencia.getStatus(), Status.ATIVO);
        }
        Mockito.verify(funcionarioRepository, Mockito.times(0)).findAll();
    }

}
