package br.com.zup.PointMarker.bancodehoras;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.bancohoras.ValidaHoras;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class BancoDeHorasServiceTeste {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    Funcionario funcionario;
    BancoDeHoras bancoDeHoras;
    Cargo cargo;

    @BeforeEach
    public void setUp() {

        cargo = new Cargo();
        cargo.setId(1);
        cargo.setNome("Jovem Aprendiz");
        cargo.setSalario(700);
        cargo.setCargoHoraria(6);

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso Guedes da Silva");
        funcionario.setStatus(Status.ATIVO);
        funcionario.setDataDeNascimento(LocalDate.of(2001, 1, 12));
        funcionario.setCpf("098.918.470-63");
        funcionario.setCargo(cargo);


        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setId(1);
        bancoDeHoras.setFuncionario(funcionario);
        bancoDeHoras.setDiaDoTrabalho(LocalDate.now());
        bancoDeHoras.setEntrada(LocalTime.now());
        bancoDeHoras.setSaida(LocalTime.now());

    }

    @Test
    public void testarCadastroDeHoras() {
        Mockito.when(bancoDeHorasRepository.save(bancoDeHoras)).thenReturn(bancoDeHoras);

        var bancoDeHorasCadastrado = bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHoras);

        Assertions.assertNotEquals(bancoDeHorasCadastrado.getFuncionario(), null);
    }

    @Test
    public void testarCadastroDeHoras_QuandoODiaForRepetido() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);

        BancoDeHoras bancoDeHorasComDataJaCadastrada = new BancoDeHoras();
        bancoDeHorasComDataJaCadastrada.setDiaDoTrabalho(bancoDeHoras.getDiaDoTrabalho());
        BancoDeHoras bancoDeHorasComErro = bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHorasComDataJaCadastrada);
        bancoDeHorasComErro.setFuncionario(funcionario);
        bancoDeHorasComErro.setDiaDoTrabalho(LocalDate.now());
        bancoDeHorasComErro.setEntrada(LocalTime.now());
        bancoDeHorasComErro.setSaida(LocalTime.now());

        Mockito.when(ValidaHoras.calcularHorasDeTrabalho(bancoDeHoras, funcionarioService, bancoDeHorasRepository)).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> {
            ValidaHoras.calcularHorasDeTrabalho(bancoDeHoras, funcionarioService, bancoDeHorasRepository);
        });

    }

    @Test
    public void exibirHorasTrabalhadasCaminhoVerdadeiro() {
        Mockito.when(funcionarioService.buscarFuncionario(Mockito.anyInt())).thenReturn(funcionario);
        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setFuncionario(funcionario);
        List<BancoDeHoras> bancoList = bancoDeHorasRepository.findAllByFuncionario(funcionario);
        for (BancoDeHoras bancoDeHoras : bancoList) {
            Assertions.assertEquals(funcionario, bancoDeHoras.getFuncionario());
        }
    }

    @Test
    public void exibirHorasTrabalhadasCaminhoEntradaCaminhoFalso() {
        Mockito.when(funcionarioService.buscarFuncionario(2)).thenReturn(null);
        List<BancoDeHoras> bancoList = bancoDeHorasService.exibirHorasTrabalhadas(2);

        Mockito.verify(bancoDeHorasRepository, Mockito.times(0)).findAllByFuncionario(funcionario);
    }

    @Test
    public void atualizarHorasTrabalhadasEntradaCaminhoVerdadeiro() {
        Mockito.when(funcionarioService.buscarFuncionario(Mockito.anyInt())).thenReturn(funcionario);
        Mockito.when(bancoDeHorasRepository.findByDiaDoTrabalho(bancoDeHoras.getDiaDoTrabalho())).thenReturn(bancoDeHoras);

        BancoDeHoras bancoTesteHorario = new BancoDeHoras();
        bancoTesteHorario.setEntrada(LocalTime.of(12, 30));
        BancoDeHoras bancoList = bancoDeHorasService.atualizarHorasTrabalhadasEntrada(Mockito.anyInt(), LocalDate.now(), bancoDeHoras);

        Assertions.assertNotEquals(bancoList.getEntrada(), bancoDeHoras.getEntrada());
    }
}
