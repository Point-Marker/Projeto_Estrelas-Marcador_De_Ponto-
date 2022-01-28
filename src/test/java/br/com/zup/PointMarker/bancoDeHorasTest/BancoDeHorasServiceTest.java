package br.com.zup.PointMarker.bancoDeHorasTest;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
public class BancoDeHorasServiceTest {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;

    private Funcionario funcionario;
    private BancoDeHoras bancoDeHoras;
    private Cargo cargo;
    private Status status;

    @BeforeEach
    public void setUp() {

        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setEntrada("5/5/2000");
        bancoDeHoras.setId(1);
        bancoDeHoras.setSaida("2000-01-01");

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
}
