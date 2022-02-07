package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.Month;

@WebMvcTest({FuncionarioController.class,JWTComponent.class})
public class FuncionarioControllerTeste {

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Funcionario funcionario;
    private Cargo cargo;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {

        cargo = new Cargo();

        cargo.setNome("Estagiario");
        cargo.setSalario(700);
        cargo.setId(1);

        usuario = new Usuario();
        usuario.setNomeUsuario("Afonso");
        usuario.setSenha("1234");

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso Benedito de Souza");
        funcionario.setCpf("159.307.330-58");
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);

        funcionario.setUsuario(usuario);
    }


    @Test
    public void testarExbicaoDeFuncionarioPeloId() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/funcionario/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

}

