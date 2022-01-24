package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.component.Conversor;
import br.com.zup.PointMarker.enums.Status;
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

@WebMvcTest({FuncionarioController.class, Conversor.class})
public class FuncionarioControllerTest {

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    private ObjectMapper objectMapper;
    private Funcionario funcionario;
    private Cargo cargo;
    private Status status;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

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

    @Test
    public void testarCadastroDeFuncionario_QuandoTodosOsDadosForemEnviadosComSucesso() throws Exception {
        Mockito.when(funcionarioService.salvarFuncionario(funcionario)).thenReturn(funcionario);

        String json = objectMapper.writeValueAsString(funcionario);

        ResultActions resultadoDaRequisicao = mockMvc.
                perform(MockMvcRequestBuilders.post("/funcionario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
