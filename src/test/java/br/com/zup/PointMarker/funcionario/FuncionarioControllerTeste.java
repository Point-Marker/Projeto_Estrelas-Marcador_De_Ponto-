package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.component.Conversor;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import br.com.zup.PointMarker.config.security.UsuarioLogado;
import br.com.zup.PointMarker.config.security.UsuarioLogadoService;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@WebMvcTest({FuncionarioController.class, JWTComponent.class, UsuarioLogadoService.class, Conversor.class})
public class FuncionarioControllerTeste {

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private UsuarioLogadoService usuarioLogadoService;

    @MockBean
    private JWTComponent jwtComponent;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Funcionario funcionario;
    private Cargo cargo;
    private Usuario usuario;
    private Authentication auth;

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
    @WithMockUser("Vander108")
    public void testarExbicaoDeFuncionarioPeloId() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/funcionario/{id}", 1)
                        .header("Authorization","Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJWYW5kZXIxMDgiLCJBdXRvcml6YWNhbyI6W3siYXV0aG9yaXR5IjoiVVNFUiJ9XSwiaWRVc3VhcmlvIjo2LCJleHAiOjE2NDQzMjc3NjJ9.wLejAPeOqda5MWQZ4qypCp3HHJPxVtkVYCaWsBjMGm6VC9CYjebS4043OM2PfU5FFphQbw5UTAQ5EW1eEr8DAw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

