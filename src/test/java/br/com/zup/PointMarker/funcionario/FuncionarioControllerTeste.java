package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.component.Conversor;
import br.com.zup.PointMarker.dtos.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioSaidaDTO;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.FuncionarioNaoEncontradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@WebMvcTest(FuncionarioController.class)
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

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        cargo = new Cargo();
        cargo.setNome("Estagiario");
        cargo.setSalario(700);
        cargo.setId(1);

        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Afonso");
        funcionario.setCpf("159.307.330-58");
        funcionario.setDataDeNascimento(LocalDate.of(1999, Month.JULY, 12));
        funcionario.setSalario(cargo.getSalario());
        funcionario.setCargo(cargo);
        funcionario.setStatus(Status.ATIVO);
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
