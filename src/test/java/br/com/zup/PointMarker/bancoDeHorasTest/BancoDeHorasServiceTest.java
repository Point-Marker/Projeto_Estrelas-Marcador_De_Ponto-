package br.com.zup.PointMarker.bancoDeHorasTest;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

@SpringBootTest
public class BancoDeHorasServiceTest {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;
    @MockBean
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;
    private BancoDeHoras bancoDeHoras;
    private Cargo cargo;
    private Status status;

    @BeforeEach
    public void setUp() {

        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setId(1);
        bancoDeHoras.setEntrada(LocalTime.of(8, 00));
        bancoDeHoras.setSaida(LocalTime.of(15, 00));
        bancoDeHoras.setDiaDoTrabalho(LocalDate.now());

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
    public void exibirHorasTrabalhadasCaminhoPositivo() {
        Mockito.when(funcionarioService.buscarFuncionario(Mockito.anyInt())).thenReturn(funcionario);
        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setFuncionario(funcionario);
        List<BancoDeHoras> bancoList = bancoDeHorasRepository.findAllByFuncionario(funcionario);
        for (BancoDeHoras bancoDeHoras : bancoList){
            Assertions.assertEquals(funcionario, bancoDeHoras.getFuncionario());
        }
    }
}
