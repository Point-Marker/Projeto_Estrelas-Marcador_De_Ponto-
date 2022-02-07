package br.com.zup.PointMarker.bancodehoras;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasController;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import br.com.zup.PointMarker.funcionario.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

@WebMvcTest({BancoDeHorasController.class, JWTComponent.class})
public class BancoDeHorasControllerTeste {

    @MockBean
    private BancoDeHorasService bancoDeHorasService;

    Funcionario funcionario;
    BancoDeHoras bancoDeHoras;

    @BeforeEach
    public void setUp() {
        funcionario = new Funcionario();
        funcionario.setId(1);

//        bancoDeHoras = new BancoDeHoras();
//        bancoDeHoras.setId(1);
//        bancoDeHoras.setId_funcionario(funcionario);
//        bancoDeHoras.setHoraExtra(1);
//        bancoDeHoras.setEntrada(LocalDateTime.now());
//        bancoDeHoras.setSaida(LocalDateTime.now());

    }
//
//    public void testarCadastroDeBancoDeHoras(){
//
//    }

}
