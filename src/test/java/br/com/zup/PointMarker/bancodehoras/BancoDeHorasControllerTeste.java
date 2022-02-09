package br.com.zup.PointMarker.bancodehoras;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasController;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@WebMvcTest({BancoDeHorasController.class, JWTComponent.class})
public class BancoDeHorasControllerTeste {

    @MockBean
    private BancoDeHorasService bancoDeHorasService;

    Funcionario funcionario;
    BancoDeHoras bancoDeHoras;
    Cargo cargo;
    Usuario usuario;

    @BeforeEach
    public void setUp() {

        cargo = new Cargo();
        cargo.setId(1);
        cargo.setNome("Jovem Aprendiz");
        cargo.setSalario(700);
        cargo.setCargahoraria(6);

        usuario = new Usuario();
        usuario.setNomeUsuario("Afonso351");
        usuario.setSenha("1234");

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso Guedes da Silva");
        funcionario.setStatus(Status.ATIVO);
        funcionario.setDataDeNascimento(LocalDate.of(2001, 1, 12));
        funcionario.setCpf("098.918.470-63");
        funcionario.setUsuario(usuario);
        funcionario.setCargo(cargo);


        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setId(1);
        bancoDeHoras.setFuncionario(funcionario);
        bancoDeHoras.setDiaDoTrabalho(LocalDate.now());
        bancoDeHoras.setEntrada(LocalTime.of(9, 0));
        bancoDeHoras.setSaida(LocalTime.of(15, 0));

    }
//
//    public void testarCadastroDeBancoDeHoras(){
//
//    }

}
