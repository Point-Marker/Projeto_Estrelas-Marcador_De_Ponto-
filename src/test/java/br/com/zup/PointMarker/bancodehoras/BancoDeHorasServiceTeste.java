package br.com.zup.PointMarker.bancodehoras;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.funcionario.Funcionario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

@SpringBootTest
public class BancoDeHorasServiceTeste {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;

    Funcionario funcionario;
    BancoDeHoras bancoDeHoras;


    @BeforeEach
    public void setUp() {
        funcionario = new Funcionario();
        funcionario.setId(1);

        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setId(1);
        bancoDeHoras.setId_funcionario(funcionario);
        bancoDeHoras.setHoraExtra(1);
        bancoDeHoras.setEntrada(LocalDateTime.now());
        bancoDeHoras.setSaida(LocalDateTime.now());

    }

    @Test
    public void testarCadastroDeHoras(){
        Mockito.when(bancoDeHorasRepository.save(bancoDeHoras)).thenReturn(bancoDeHoras);

        var bancoDeHorasCadastrado = bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHoras);

        Assertions.assertNotEquals(bancoDeHorasCadastrado.getId_funcionario(),null);
    }
}
