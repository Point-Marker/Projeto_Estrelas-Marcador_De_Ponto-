package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.exceptions.HorarioInvalidoException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;

import java.util.List;

class ValidaHoras {


    public static int calcularHorasDeTrabalho(BancoDeHoras bancoDeHoras, FuncionarioService funcionarioService,
                                              BancoDeHorasRepository bancoDeHorasRepository) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(bancoDeHoras.getFuncionario().getId());

        try {

            if (validarHorasLancadas(bancoDeHoras, bancoDeHorasRepository)) {
                final int LIMITE_DE_HORAS_TRABALHADAS = 50;

                if (funcionario.getTotalHorasTrabalhadas() <= LIMITE_DE_HORAS_TRABALHADAS) {
                    funcionario.setTotalHorasTrabalhadas(funcionario.getTotalHorasTrabalhadas() + contadorDeHoras(bancoDeHoras));
                    bancoDeHoras.setFuncionario(funcionario);
                    bancoDeHorasRepository.save(bancoDeHoras);
                } else {
                    throw new RuntimeException("Você já excedeu as horas trabalhadas.");
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
            if (referencia.getDiaDoTrabalho().equals(bancoDeHoras.getDiaDoTrabalho())) {
                throw new RuntimeException("Este dia já foi cadastrado.");
            }
        }
        return true;
    }

    private static int contadorDeHoras(BancoDeHoras bancoDeHoras) {
        int entrada = bancoDeHoras.getEntrada().getHour();
        int saida = bancoDeHoras.getEntrada().getHour();

        int horasTrabalhadas = saida - entrada;

        if (horasTrabalhadas > 8) {
            throw new RuntimeException("Não É Permitido Cadastrar Mais Do Que 8 Horas Trabalhadas.");
        }
        return horasTrabalhadas;
    }

}
