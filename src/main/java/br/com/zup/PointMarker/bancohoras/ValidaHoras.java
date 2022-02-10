package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.exceptions.CargaHorariaUltrapassadaException;
import br.com.zup.PointMarker.exceptions.HorarioInvalidoException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;

import java.time.LocalDate;
import java.time.LocalTime;
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

                if (horasTrabalhadas != bancoDeHoras.getFuncionario().getCargo().getCargahoraria()) {
                    int horaExtra = bancoDeHoras.getFuncionario().getCargo().getCargahoraria() + 2;
                    if (horasTrabalhadas > horaExtra) {
                        throw new CargaHorariaUltrapassadaException("A sua Carga Horária é de: "
                                + bancoDeHoras.getFuncionario().getCargo().getCargahoraria());
                    }
                }
                int totalHorasExtras = horasTrabalhadas - bancoDeHoras.getFuncionario().getCargo().getCargahoraria();
                bancoDeHoras.getFuncionario().setHorasExtras(bancoDeHoras.getFuncionario().getHorasExtras()
                        + totalHorasExtras);

                if (funcionario.getTotalHorasTrabalhadas() <= LIMITE_DE_HORAS_TRABALHADAS) {
                    funcionario.setTotalHorasTrabalhadas(funcionario.getTotalHorasTrabalhadas() + horasTrabalhadas);
                    bancoDeHoras.setFuncionario(funcionario);
                    bancoDeHorasRepository.save(bancoDeHoras);
                }
            }
            return funcionario.getTotalHorasTrabalhadas();
        } catch (HorarioInvalidoException horarioInvalido) {
            throw new HorarioInvalidoException(horarioInvalido.getMessage());
        }
    }

    public static boolean validarHorasEntradaESaida(BancoDeHoras bancoDeHoras) {

        LocalTime horaLimiteEntrada = LocalTime.of(7, 59);
        LocalTime horaLimiteSaida = LocalTime.of(22, 00);

        if (!bancoDeHoras.getEntrada().isBefore(horaLimiteEntrada) & !bancoDeHoras.getSaida().isAfter(horaLimiteSaida)) {
            if (!bancoDeHoras.getEntrada().isAfter(bancoDeHoras.getSaida()) & !bancoDeHoras.getSaida().isBefore(bancoDeHoras.getEntrada())) {
                if (verificarCargaHorariaDeAcordoComAsHorasTrabalhadas(bancoDeHoras)) {
                    return true;
                }
            }
            throw new HorarioInvalidoException("A hora de entrada não pode ser depois da hora de saída do trabalho.");
        }

        return false;
    }

    public static boolean validarHorasLancadas(BancoDeHoras bancoDeHoras, BancoDeHorasRepository bancoDeHorasRepository) {

        if (bancoDeHoras.getEntrada().isAfter(bancoDeHoras.getSaida()) & bancoDeHoras.getSaida().isBefore(bancoDeHoras.getEntrada())) {
            throw new HorarioInvalidoException("A hora de entrada não pode ser depois da hora de saída do trabalho.");
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

    public static boolean verificarCargaHorariaDeAcordoComAsHorasTrabalhadas(BancoDeHoras bancoDeHoras) {
        int entrada = bancoDeHoras.getEntrada().getHour();
        int saida = bancoDeHoras.getSaida().getHour();

        int horasTrabalhadas = saida - entrada;

        if (horasTrabalhadas != bancoDeHoras.getFuncionario().getCargo().getCargahoraria()) {
            throw new CargaHorariaUltrapassadaException("A sua Carga Horária é de: "
                    + bancoDeHoras.getFuncionario().getCargo().getCargahoraria());
        }
        return true;
    }
}
