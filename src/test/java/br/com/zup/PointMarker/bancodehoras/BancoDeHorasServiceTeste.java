package br.com.zup.PointMarker.bancodehoras;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasRepository;
import br.com.zup.PointMarker.bancohoras.BancoDeHorasService;
import br.com.zup.PointMarker.bancohoras.ValidaHoras;
import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.CargaHorariaUltrapassadaException;
import br.com.zup.PointMarker.exceptions.HoraLimiteEntradaESaidaException;
import br.com.zup.PointMarker.exceptions.TotalDeHorasTrabalhadasUltrapassadaException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioRepository;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import br.com.zup.PointMarker.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class BancoDeHorasServiceTeste {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @MockBean
    private BancoDeHorasRepository bancoDeHorasRepository;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @MockBean
    private ValidaHoras validaHoras;

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

    @Test
    public void testarVerificacaoDeHorasQuandoRetornarTrue() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);

        boolean valorTrue = bancoDeHorasService.verificarHorasTrabalhadadas(bancoDeHoras);

        Assertions.assertTrue(valorTrue);
    }

    @Test
    public void testarCadastroDeHorasQuandoTodosOsDadosForemEnviadosComSucesso() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);
        Mockito.when(bancoDeHorasService.verificarHorasTrabalhadadas(bancoDeHoras)).thenReturn(true);
        Mockito.when(bancoDeHorasRepository.save(Mockito.any(BancoDeHoras.class))).thenReturn(bancoDeHoras);

        BancoDeHoras bancoDeHorasSalvo = bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHoras);

        Assertions.assertEquals(bancoDeHorasSalvo, bancoDeHoras);
    }

    @Test
    public void testarCadastroDeHorasQuandoOTotalDeHorasEstaMaiorQueCinquentaHorasTrabalhadas() {
        bancoDeHoras.getFuncionario().setTotalHorasTrabalhadas(50);

        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);
        Mockito.when(bancoDeHorasService.verificarHorasTrabalhadadas(bancoDeHoras)).thenReturn(false);

        Assertions.assertThrows(TotalDeHorasTrabalhadasUltrapassadaException.class, () ->
                bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHoras));

    }

    @Test
    public void testarCadastroDeHorasQuandoExcedeACargaHoraria() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);
        Mockito.when(bancoDeHorasService.verificarHorasTrabalhadadas(bancoDeHoras)).thenReturn(false);

        if (funcionario.getTotalHorasTrabalhadas() > funcionario.getCargo().getCargahoraria()) {
            Assertions.assertThrows(CargaHorariaUltrapassadaException.class, () ->
                    bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHoras));
        }

    }

    @Test
    public void testarExibirHorasTrabalhadasQuandoTodosOsDadosForemEnviadosComSucesso() {
        Mockito.when(funcionarioService.buscarFuncionario(Mockito.anyInt())).thenReturn(funcionario);
        bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setFuncionario(funcionario);
        List<BancoDeHoras> bancoList = bancoDeHorasRepository.findAllByFuncionario(funcionario);
        for (BancoDeHoras bancoDeHoras : bancoList) {
            Assertions.assertEquals(funcionario, bancoDeHoras.getFuncionario());
        }
    }

    @Test
    public void testarExibirHorasTrabalhadasCaminhoEntradaCaminhoNegativoCasoFuncionarioRetorneNull() {
        Mockito.when(funcionarioService.buscarFuncionario(2)).thenReturn(null);
        List<BancoDeHoras> bancoList = bancoDeHorasService.exibirHorasTrabalhadas(2);

        Mockito.verify(bancoDeHorasRepository, Mockito.times(0)).findAllByFuncionario(funcionario);
    }

    @Test
    public void testarAtualizarHorasTrabalhadasEntradaQuandoTodosOsDadosForemEnviadosComSucesso() {
        bancoDeHoras.setEntrada(LocalTime.of(8, 00));
        bancoDeHoras.setSaida(LocalTime.of(14, 00));

        Mockito.when(funcionarioService.buscarFuncionario(Mockito.anyInt())).thenReturn(funcionario);
        Mockito.when(bancoDeHorasRepository.findByDiaDoTrabalho(bancoDeHoras.getDiaDoTrabalho()))
                .thenReturn(bancoDeHoras);

        BancoDeHoras bancoComHoraDeEntradaAtualizado = bancoDeHorasService.atualizarHorasTrabalhadas(1,
                LocalDate.now(), bancoDeHoras);

        Assertions.assertEquals(bancoComHoraDeEntradaAtualizado.getEntrada(), bancoDeHoras.getEntrada());
    }

    @Test
    public void testarExibirTodosBancosDeHorasQuandoTodosOsDadosForemEnviadosComSucesso() {
        Mockito.when(bancoDeHorasRepository.findAll()).thenReturn(List.of(bancoDeHoras));

        List<BancoDeHoras> bancoDeHorasExibidos = bancoDeHorasService.exibirTodosBancosDeHoras();
        for (BancoDeHoras bancoDeHorasReferencia : bancoDeHorasExibidos) {
            Assertions.assertEquals(bancoDeHorasReferencia, bancoDeHoras);
        }

    }

    @Test
    public void testarExibirHorasExtrasTrabalhadasQuandoTodosOsDadosForemEnviadosComSucesso() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);

    }

    @Test
    public void testarDeletarHorasTrabalhadas() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);

        bancoDeHorasService.removerHorasFuncionario(1);
        List<BancoDeHoras> bancoDeHorasExibidos = bancoDeHorasRepository.findAllByFuncionario(funcionario);
        Assertions.assertTrue(bancoDeHorasExibidos.isEmpty());
    }

    @Test
    public void testarAtualizarHorasTrabalhadasEntradaCaminhoPositivo() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);
        Mockito.when(bancoDeHorasRepository.findByDiaDoTrabalho(LocalDate.now())).thenReturn(bancoDeHoras);
        Mockito.when(bancoDeHorasRepository.save(Mockito.any(BancoDeHoras.class))).thenReturn(bancoDeHoras);

        BancoDeHoras bancoDeHorasASerAtualizado = new BancoDeHoras();
        bancoDeHorasASerAtualizado.setEntrada(LocalTime.of(10, 00));
        bancoDeHorasASerAtualizado.setSaida(LocalTime.of(16, 00));
        bancoDeHorasASerAtualizado.setFuncionario(funcionario);

        BancoDeHoras bancoDeHorasComHorarioAtualizado = bancoDeHorasService.atualizarHorasTrabalhadas(1, LocalDate.now(),
                bancoDeHorasASerAtualizado);

        Assertions.assertEquals(bancoDeHorasComHorarioAtualizado.getEntrada(), LocalTime.of(10, 0));
    }

    @Test
    public void testarAtualizarHorasTrabalhadasEntradaCaminhoNegativoRecebendoEntradaErrada() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);
        Mockito.when(bancoDeHorasRepository.findByDiaDoTrabalho(LocalDate.now())).thenReturn(bancoDeHoras);
        Mockito.when(bancoDeHorasRepository.save(Mockito.any(BancoDeHoras.class))).thenReturn(bancoDeHoras);

        BancoDeHoras bancoDeHorasASerAtualizado = new BancoDeHoras();
        bancoDeHorasASerAtualizado.setEntrada(LocalTime.of(7, 00));
        bancoDeHorasASerAtualizado.setSaida(LocalTime.of(16, 00));
        bancoDeHorasASerAtualizado.setFuncionario(funcionario);

        Assertions.assertThrows(HoraLimiteEntradaESaidaException.class, () ->
                bancoDeHorasService.atualizarHorasTrabalhadas(1, LocalDate.now(), bancoDeHorasASerAtualizado));
    }

    @Test
    public void testarAtualizarHorasTrabalhadasEntradaCaminhoNegativoRecebendoSaidaErrada() {
        Mockito.when(funcionarioService.buscarFuncionario(1)).thenReturn(funcionario);
        Mockito.when(bancoDeHorasRepository.findByDiaDoTrabalho(LocalDate.now())).thenReturn(bancoDeHoras);
        Mockito.when(bancoDeHorasRepository.save(Mockito.any(BancoDeHoras.class))).thenReturn(bancoDeHoras);

        BancoDeHoras bancoDeHorasASerAtualizado = new BancoDeHoras();
        bancoDeHorasASerAtualizado.setEntrada(LocalTime.of(8, 00));
        bancoDeHorasASerAtualizado.setSaida(LocalTime.of(23, 00));
        bancoDeHorasASerAtualizado.setFuncionario(funcionario);

        Assertions.assertThrows(HoraLimiteEntradaESaidaException.class, () ->
                bancoDeHorasService.atualizarHorasTrabalhadas(1, LocalDate.now(), bancoDeHorasASerAtualizado));
    }

}
