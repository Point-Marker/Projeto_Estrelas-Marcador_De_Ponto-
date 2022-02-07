package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.Month;

@WebMvcTest({GestorController.class, JWTComponent.class})
public class GestorControllerTest {

    @MockBean
    private GestorService gestorService;

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
        funcionario.setNome("Afonso Benedito de Souza   ");
        funcionario.setCpf("159.307.330-58");
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);

        funcionario.setUsuario(usuario);
    }


    @Test
    public void testarCadastroDeFuncionario_QuandoTodosOsDadosForemEnviadosComSucesso() throws Exception {
        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);

        String json = objectMapper.writeValueAsString(funcionario);

        ResultActions resultadoDaRequisicao =
                mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/cadastro/funcionarios")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testarCadastroDeFuncionario_QuandoOCampoNomeEstiverVazioNaoDeveCadastrarOFuncionario() throws Exception {

        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);
        funcionario.setNome("");

        String json = objectMapper.writeValueAsString(funcionario);


        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/funcionario")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void testarCadastroDeFuncionario_QuandoONomeNaoEstiverComDezCaracters() throws Exception {

        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);
        funcionario.setNome("Alonso");

        String json = objectMapper.writeValueAsString(funcionario);


        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/funcionario")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void testarCadastroDeFuncionario_QuandoONomeNaoEstiverComMenosDoQueTrintaCaracters() throws Exception {

        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);
        funcionario.setNome("Alonso Conrado Francisco De Juapinba Nevez Meireles");

        String json = objectMapper.writeValueAsString(funcionario);


        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/funcionario")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void atualizarSalarioCaminhoPositivo() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/funcionario/salario/{id}", 1)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void atualizarSalarioCaminhoNegativo() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/funcionario/salario/{id}", "")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void atualizarCargoCaminhoPositivo() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/funcionario/cargo/{id}", 1)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void atualizarCargoCaminhoNegativo() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/funcionario/cargo/{id}", "")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void atualizarStatusCaminhoPositivo() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/funcionario/status/{id}", 1)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void atualizarStatusCaminhoNegativo() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/funcionario/status/{id}", "")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
