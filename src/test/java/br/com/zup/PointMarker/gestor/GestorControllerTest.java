package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.component.Conversor;
import br.com.zup.PointMarker.config.security.JWT.JWTComponent;
import br.com.zup.PointMarker.config.security.UsuarioLogadoService;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.FuncionarioComStatusInativoException;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import br.com.zup.PointMarker.exceptions.LimiteAumentoSalarioException;
import br.com.zup.PointMarker.exceptions.MaisDeCinquentaHorasTrabalhadasException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarCargoDTO.AtualizarCargoEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarSalarioDTO.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarStatusDTO.AtualizarStatusEntradaDTO;
import br.com.zup.PointMarker.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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
import java.time.Month;

import static br.com.zup.PointMarker.enums.Status.ATIVO;
import static br.com.zup.PointMarker.enums.Status.INATIVO;

@WebMvcTest({GestorController.class, JWTComponent.class, UsuarioLogadoService.class, Conversor.class})
public class GestorControllerTest {

    @MockBean
    private GestorService gestorService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UsuarioLogadoService usuarioLogadoService;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;
    private Funcionario funcionario;
    private Cargo cargo;
    private Usuario usuario;
    private Status status;
    private AtualizarSalarioEntradaDTO atualizarSalarioEntradaDTO;
    private AtualizarCargoEntradaDTO atualizarCargoEntradaDTO;
    private AtualizarStatusEntradaDTO atualizarStatusEntradaDTO;



    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setSalario(700);
        cargo.setId(1);

        usuario = new Usuario();
        usuario.setNomeUsuario("Afonso");
        usuario.setSenha("1234");

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso Lima de Oliveira");
        funcionario.setCpf("159.307.330-58");
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(ATIVO);
        funcionario.setUsuario(usuario);

        atualizarSalarioEntradaDTO = new AtualizarSalarioEntradaDTO();
        atualizarSalarioEntradaDTO.setSalario(200);

        atualizarStatusEntradaDTO = new AtualizarStatusEntradaDTO();
        atualizarStatusEntradaDTO.setStatus(INATIVO);

        atualizarCargoEntradaDTO = new AtualizarCargoEntradaDTO();
        atualizarCargoEntradaDTO.setCargo(cargo);
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testarCadastroDeUmFuncionarioQuandoTodosOsDadosForemEnviadosComSucesso() throws Exception {
        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);

        String json = objectMapper.writeValueAsString(funcionario);

        ResultActions resultadoDaRequisicao =
                mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/cadastro/funcionarios")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testarCadastroDeUmFuncionarioQuandoOCampoNomeEstiverVazioNaoDeveCadastrarOFuncionario() throws Exception {

        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);
        funcionario.setNome("");

        String json = objectMapper.writeValueAsString(funcionario);


        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/cadastro/funcionarios")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testarCadastroDeUmFuncionarioQuandoONomeNaoEstiverComDezCaracteres() throws Exception {

        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);
        funcionario.setNome("Alonso");

        String json = objectMapper.writeValueAsString(funcionario);


        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/cadastro/funcionarios")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testarCadastroDeFuncionario_QuandoONomeNaoEstiverComMenosDoQueTrintaCaracteres() throws Exception {

        Mockito.when(gestorService.cadastrarFuncionario(funcionario)).thenReturn(funcionario);
        funcionario.setNome("Alonso Conrado Francisco De Juapinba Nevez Meireles");

        String json = objectMapper.writeValueAsString(funcionario);


        ResultActions resultActions =
                mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/cadastro/funcionarios")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "Afonso", authorities = "ADMIN")
    public void testarAtualizarSalarioQuandoTodosOsDadosForemEnviadosComSucesso() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/dashboard/salario/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "Afonso", authorities = "ADMIN")
    public void testarAtualizarSalarioCaminhoNegativoCasoSalarioSejaMaiorQueSalarioFuncionario() throws Exception {
        Mockito.doThrow(LimiteAumentoSalarioException.class).when(gestorService).atualizarSalario(1, 800);
        atualizarSalarioEntradaDTO.setSalario(800);
        String json = objectMapper.writeValueAsString(atualizarSalarioEntradaDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/dashboard/salario/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "Afonso", authorities = "ADMIN")
    public void testarAtualizarCargoQuandoTodosOsDadosForemEnviadosComSucesso() throws Exception {
        String json = objectMapper.writeValueAsString(funcionario);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/dashboard/cargo/1" )
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "Afonso", authorities = "ADMIN")
    public void testarAtualizarCargoCaminhoNegativoCasoFuncionarioInformadoNaoForValido() throws Exception {

        Funcionario funcionarioStatus = new Funcionario();
        funcionarioStatus.setStatus(INATIVO);
        funcionarioStatus.setId(3);

        Mockito.doThrow(FuncionarioComStatusInativoException.class).when(gestorService).atualizarCargo(3, cargo);
        String json = objectMapper.writeValueAsString(funcionarioStatus);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/dashboard/cargo/3")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(username = "Afonso", authorities = "ADMIN")
    public void atualizarStatusQuandoTodosOsDadosForemEnviadosComSucesso() throws Exception {
        String json = objectMapper.writeValueAsString(atualizarStatusEntradaDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/dashboard/status/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "Afonso", authorities = "ADMIN")
    public void testarAtualizarStatusCaminhoNegativoCasoTotalDeHorasTrabalhadasForMaiorQueCinquenta() throws Exception {
        funcionario.setTotalHorasTrabalhadas(99);
        Mockito.doThrow(MaisDeCinquentaHorasTrabalhadasException.class).when(gestorService).atualizarStatus(1, status);

        String json = objectMapper.writeValueAsString(atualizarSalarioEntradaDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/dashboard/status/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}
