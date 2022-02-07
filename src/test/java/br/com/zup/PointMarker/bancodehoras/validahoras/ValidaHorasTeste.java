package br.com.zup.PointMarker.bancodehoras.validahoras;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.bancohoras.ValidaHoras;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
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
public class ValidaHorasTeste {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;

    @MockBean
    private FuncionarioService funcionarioService;

    Funcionario funcionario;
    BancoDeHoras bancoDeHoras;
    Cargo cargo;

    @BeforeEach
    public void setUp() {

        cargo = new Cargo();
        cargo.setId(1);
        cargo.setNome("Jovem Aprendiz");
        cargo.setSalario(700);
        cargo.setCargahoraria(6);

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

        bancoDeHorasRepository.save(bancoDeHoras);
    }

    @Test
    public void diaJaInseridoNoSistema_QuandoTemExcecao() {
        Mockito.when(bancoDeHorasRepository.findAllByFuncionario(funcionario)).thenReturn(List.of(bancoDeHoras));

        BancoDeHoras bancoDeHorasComDataRepetida = new BancoDeHoras();
        bancoDeHorasComDataRepetida.setDiaDoTrabalho(LocalDate.now());
        bancoDeHorasComDataRepetida.setId(1);
        bancoDeHorasComDataRepetida.setFuncionario(funcionario);
        bancoDeHorasComDataRepetida.setDiaDoTrabalho(LocalDate.now());
        bancoDeHorasComDataRepetida.setEntrada(LocalTime.now());
        bancoDeHorasComDataRepetida.setSaida(LocalTime.now());

        Assertions.assertThrows(RuntimeException.class, () -> {
            ValidaHoras.horaJaInseridaNoSistema(bancoDeHorasRepository, bancoDeHoras);
        });
    }

//    @Test
//    public void diaJaInseridoNoSistemaCaminhoBom_QuandoODiaNaoEstaCadastradoNoSistema() {
//
//    }
}
