package br.com.zup.PointMarker.bancohoras;


import br.com.zup.PointMarker.exceptions.HorarioInvalidoException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BancoDeHorasService {

    @Autowired
    private BancoDeHorasRepository bancoDeHorasRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    int totalHorasTrabalhadas = 0;


    public BancoDeHoras salvarHorasTrabalhadas(BancoDeHoras bancoDeHoras) {

        Funcionario funcionario = funcionarioService.buscarFuncionario(bancoDeHoras.getId_funcionario().getId());
        if (verificarHorasTrabalhadadas(bancoDeHoras.getId_funcionario().getId(), bancoDeHoras))
            bancoDeHoras.setId_funcionario(funcionario);

        return bancoDeHorasRepository.save(bancoDeHoras);

    }

    public boolean verificarHorasTrabalhadadas(int id, BancoDeHoras bancoDeHoras) {

        if (calcularHorasDeTrabalho(bancoDeHoras) <= 50) {
            return true;
        } else {
            return false;
        }
    }

    public int calcularHorasDeTrabalho(BancoDeHoras bancoDeHoras) {

        try {
            if (validarHorasLancadas(bancoDeHoras)) {
                final int LIMITE_DE_HORAS_TRABALHADAS = 50;
                int entrada = bancoDeHoras.getEntrada().getHour();
                int saida = bancoDeHoras.getSaida().getHour();
                int horasTrabalhadas = 0;
                int capturarHoras = 0;

                for (int i = entrada; i <= saida; i++) {
                    horasTrabalhadas += 1;
                    if (horasTrabalhadas > 8) {
                        throw new RuntimeException("Não É Permitido Cadastrar Mais Do Que 8 Horas Trabalhadas.");
                    }
                    capturarHoras = horasTrabalhadas;
                }
                totalHorasTrabalhadas += capturarHoras;

                if (totalHorasTrabalhadas > LIMITE_DE_HORAS_TRABALHADAS) {
                    throw new RuntimeException("Você já excedeu as horas trabalhadas.");
                }
            }
            return totalHorasTrabalhadas;
        } catch (HorarioInvalidoException horarioInvalido) {
            throw new HorarioInvalidoException(horarioInvalido.getMessage());
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public boolean validarHorasLancadas(BancoDeHoras bancoDeHoras) {

        if (bancoDeHoras.getEntrada().isAfter(bancoDeHoras.getSaida())) {
            throw new HorarioInvalidoException("A Hora De Entrada Não Pode ser Depois da Hora de Saida Do Trabalho.");
        }
        return true;
    }
}
