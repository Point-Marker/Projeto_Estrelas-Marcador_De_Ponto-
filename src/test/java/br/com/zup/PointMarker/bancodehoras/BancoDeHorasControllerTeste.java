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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@WebMvcTest({BancoDeHorasController.class, JWTComponent.class})
public class BancoDeHorasControllerTeste {

    @MockBean
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasController bancoDeHorasController;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Funcionario funcionario;
    private BancoDeHoras bancoDeHoras;
    private Cargo cargo;
    private Usuario usuario;
    private Status status;

    @BeforeEach
    public void setUp() {

        cargo = new Cargo();
        cargo.setId(1);
        cargo.setNome("Jovem Aprendiz");
        cargo.setSalario(700);
        cargo.setCargahoraria(6);

        usuario = new Usuario();
        usuario.setNomeUsuario("Afonso");
        usuario.setSenha("1234");

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso Guedes da Silva");
        funcionario.setStatus(Status.ATIVO);
        funcionario.setDataDeNascimento(LocalDate.of(2001, 1, 12));
        funcionario.setCpf("098.918.470-63");
        funcionario.setUsuario(usuario);
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);


        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setId(1);
        bancoDeHoras.setFuncionario(funcionario);
        bancoDeHoras.setDiaDoTrabalho(LocalDate.now());
        bancoDeHoras.setEntrada(LocalTime.of(9, 0));
        bancoDeHoras.setSaida(LocalTime.of(15, 0));

    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testarDeletarHorasFuncionario() throws Exception {
        Mockito.doNothing().when(bancoDeHorasService).removerHorasFuncionario(1);

        ResultActions resultActions = mockMvc.perform
                (MockMvcRequestBuilders.delete("/bancohoras/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is((204))));
    }

}