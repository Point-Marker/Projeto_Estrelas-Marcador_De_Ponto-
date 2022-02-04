package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.exceptions.ASuaCargaHorariaException;
import br.com.zup.PointMarker.exceptions.HorarioInvalidoException;
import br.com.zup.PointMarker.exceptions.VoceExcedeuAsHorasTrabalhadasException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioRepository;
import br.com.zup.PointMarker.funcionario.FuncionarioService;

import java.time.LocalDate;
import java.util.List;

public class ValidaHoras {


    public static int calcularHorasDeTrabalho(BancoDeHoras bancoDeHoras, FuncionarioService funcionarioService,
                                              BancoDeHorasRepository bancoDeHorasRepository) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(bancoDeHoras.getFuncionario().getId());

        try {

            if (validarHorasLancadas(bancoDeHoras, bancoDeHorasRepository)) {
                final int LIMITE_DE_HORAS_TRABALHADAS = 50;

                int entrada = bancoDeHoras.getEntrada().getHour();
                int saida = bancoDeHoras.getSaida().getHour();

                int horasTrabalhadas = saida - entrada;

                if (horasTrabalhadas != funcionario.getCargo().getCargoHoraria()) {
                    throw new ASuaCargaHorariaException("A sua Carga Horaria é de: "
                            + funcionario.getCargo().getCargoHoraria());
                }

                if (funcionario.getTotalHorasTrabalhadas() <= LIMITE_DE_HORAS_TRABALHADAS) {
                    funcionario.setTotalHorasTrabalhadas(funcionario.getTotalHorasTrabalhadas() + horasTrabalhadas);
                    bancoDeHoras.setFuncionario(funcionario);
                    bancoDeHorasRepository.save(bancoDeHoras);
                } else {
                    throw new VoceExcedeuAsHorasTrabalhadasException("Você excedeu sua carga horária de trabalho!");
                }
            }
            return funcionario.getTotalHorasTrabalhadas();
        } catch (HorarioInvalidoException horarioInvalido) {
            throw new HorarioInvalidoException(horarioInvalido.getMessage());
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public static boolean validarHorasLancadas(BancoDeHoras bancoDeHoras, BancoDeHorasRepository bancoDeHorasRepository) {

        if (bancoDeHoras.getEntrada().isAfter(bancoDeHoras.getSaida()) & bancoDeHoras.getSaida().isBefore(bancoDeHoras.getEntrada())) {
            throw new HorarioInvalidoException("A Hora De Entrada Não Pode ser Depois da Hora de Saida Do Trabalho.");
        }

        horaJaInseridaNoSistema(bancoDeHorasRepository, bancoDeHoras);

        return true;
    }

    public static boolean horaJaInseridaNoSistema(BancoDeHorasRepository bancoDeHorasRepository, BancoDeHoras bancoDeHoras) {
        List<BancoDeHoras> bancoDeHorasList = bancoDeHorasRepository.findAllByFuncionario(bancoDeHoras.getFuncionario());

        for (BancoDeHoras referencia : bancoDeHorasList) {
            if (referencia.getDiaDoTrabalho().equals(LocalDate.now())) {
                throw new HorarioInvalidoException("O dia informado já foi cadastrado!");
            }
        }
        return true;
    }

    private static int contadorDeHoras(BancoDeHoras bancoDeHoras) {
        int entrada = bancoDeHoras.getEntrada().getHour();
        int saida = bancoDeHoras.getSaida().getHour();

        int horasTrabalhadas = saida - entrada;

        if (horasTrabalhadas == bancoDeHoras.getFuncionario().getCargo().getCargoHoraria()) {
            return horasTrabalhadas;
        }

        throw new ASuaCargaHorariaException("A sua Carga Horaria é de: "
                + bancoDeHoras.getFuncionario().getCargo().getCargoHoraria());
    }

}
