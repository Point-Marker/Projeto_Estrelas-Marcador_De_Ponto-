package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.exceptions.ASuaCargaHorariaException;
import br.com.zup.PointMarker.exceptions.HoraLimiteEntradaESaidaException;
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

                if (horasTrabalhadas != funcionario.getCargo().getCargahoraria()) {
                    throw new ASuaCargaHorariaException("A sua Carga Horaria é de: "
                            + funcionario.getCargo().getCargahoraria());
                }

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

        if (bancoDeHoras.getEntrada().isBefore(horaLimiteEntrada) || bancoDeHoras.getEntrada().isAfter(horaLimiteSaida)) {
            throw new HoraLimiteEntradaESaidaException("A hora registrada não pode ser antes das 08:00 da manhã ou depois das 22:00 da noite.");

        } else if (bancoDeHoras.getSaida().isBefore(horaLimiteEntrada) || bancoDeHoras.getSaida().isAfter(horaLimiteSaida)) {
            throw new HoraLimiteEntradaESaidaException("A hora registrada não pode ser antes das 08:00 da manhã ou depois das 22:00 da noite.");
        }

        return true;
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

}
