package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BancoDeHorasService {

    @Autowired
    private BancoDeHorasRepository bancoDeHorasRepository;

    @Autowired
    private FuncionarioService funcionarioService;


    public BancoDeHoras salvarHorasTrabalhadas(BancoDeHoras bancoDeHoras) {

        Funcionario funcionario = funcionarioService.buscarFuncionario(bancoDeHoras.getFuncionario().getId());
        if (verificarHorasTrabalhadadas(bancoDeHoras)) {
            bancoDeHoras.setFuncionario(funcionario);
        }

        bancoDeHoras.setDiaDoTrabalho(LocalDate.now());
        return bancoDeHorasRepository.save(bancoDeHoras);
    }

    public boolean verificarHorasTrabalhadadas(BancoDeHoras bancoDeHoras) {

        if (ValidaHoras.calcularHorasDeTrabalho(bancoDeHoras, funcionarioService, bancoDeHorasRepository) <= 50) {
            return true;
        } else {
            return false;
        }
    }

    public List<BancoDeHoras> exibirHorasTrabalhadas(int id) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
        BancoDeHoras bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setFuncionario(funcionario);
        return bancoDeHorasRepository.findAllByFuncionario(bancoDeHoras.getFuncionario());
    }
}
