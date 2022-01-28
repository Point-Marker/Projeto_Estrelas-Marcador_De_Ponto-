package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        bancoDeHoras.setFuncionario(funcionario);
        return bancoDeHorasRepository.findAllByFuncionario(bancoDeHoras.getFuncionario());
    }

    public BancoDeHoras atualizarHorasTrabalhadasEntrada(int id, LocalDateTime data, BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        BancoDeHoras banco = bancoDeHorasRepository.findByEntrada(data);
        banco.setEntrada(bancoDeHoras.getEntrada());
        bancoDeHorasRepository.save(banco);

        return bancoDeHoras;
    }

    public BancoDeHoras atualizarHorasTrabalhadasSaida(int id, LocalDateTime data, BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        BancoDeHoras banco = bancoDeHorasRepository.findByEntrada(data);
        banco.setSaida(bancoDeHoras.getSaida());
        bancoDeHorasRepository.save(banco);

        return bancoDeHoras;
    }

    public void removerHorasFuncionario (int id){
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
        funcionarioService.deletarHorasTrabalhadas(id);

        BancoDeHoras banco = new BancoDeHoras();
        banco.setId_funcionario(funcionario);
        bancoDeHorasRepository.delete(banco);

    }
}
