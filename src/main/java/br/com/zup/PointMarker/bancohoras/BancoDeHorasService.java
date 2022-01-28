package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoDeHorasService {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private BancoDeHorasRepository bancoDeHorasRepository;

    public List<BancoDeHoras> exibirHorasTrabalhadas(int id) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
        BancoDeHoras bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setId_funcionario(funcionario);
        return bancoDeHorasRepository.findAllById_Funcionario(bancoDeHoras.getId_funcionario());
    }
}
