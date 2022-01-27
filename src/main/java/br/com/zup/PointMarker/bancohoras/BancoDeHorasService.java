package br.com.zup.PointMarker.bancohoras;


import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BancoDeHorasService {

    @Autowired
    private BancoDeHorasRepository bancoDeHorasRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    int totalHorasTrabalhadas = 0;


    public BancoDeHoras salvarHorasTrabalhadas(BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(bancoDeHoras.getId_funcionario().getId());

        if (verificarHorasTrabalhadadas(bancoDeHoras.getId_funcionario().getId(), bancoDeHoras)) {
            bancoDeHoras.setId_funcionario(funcionario);
            return bancoDeHorasRepository.save(bancoDeHoras);
        }

        throw new RuntimeException("Você já excedeu as horas trabalhadas.");
    }

    public boolean verificarHorasTrabalhadadas(int id, BancoDeHoras bancoDeHoras) {
        List<BancoDeHoras> listaDeHorasTrabalhadas = new ArrayList<>();

        for (BancoDeHoras referencia : listaDeHorasTrabalhadas) {
            if (bancoDeHoras.getId() == id) {
                listaDeHorasTrabalhadas.add(bancoDeHoras);
                return true;
            } else {
                if (calcularHorasDeTrabalho(bancoDeHoras) > 10) {
                    return false;
                }
            }
        }
        return true;
    }

    public int calcularHorasDeTrabalho(BancoDeHoras bancoDeHoras) {
        final int LIMITE_DE_HORAS_TRABALHADAS = 30;

        int entrada = bancoDeHoras.getEntrada().getHour();
        int saida = bancoDeHoras.getSaida().getHour();
        int horasTrabalhadas = 0;
        int capturarHoras = 0;

        for (int i = entrada; i <= saida; i++) {
            horasTrabalhadas += 1;
            capturarHoras = horasTrabalhadas;
        }
        totalHorasTrabalhadas += capturarHoras;

        if (totalHorasTrabalhadas <= LIMITE_DE_HORAS_TRABALHADAS) {
            return totalHorasTrabalhadas;
        }

        throw new RuntimeException("Excedeu");
    }
}
