package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.BancoDeHorasNãoEncontradoException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BancoDeHorasService {

    private BancoDeHorasRepository bancoDeHorasRepository;
    private FuncionarioService funcionarioService;

    @Autowired
    public BancoDeHorasService(BancoDeHorasRepository bancoDeHorasRepository, FuncionarioService funcionarioService) {
        this.bancoDeHorasRepository = bancoDeHorasRepository;
        this.funcionarioService = funcionarioService;
    }


    public BancoDeHoras salvarHorasTrabalhadas(BancoDeHoras bancoDeHoras) {

        if (verificarHorasTrabalhadadas(bancoDeHoras)) {
            bancoDeHoras.setDiaDoTrabalho(LocalDate.now());
            return bancoDeHorasRepository.save(bancoDeHoras);
        }

        throw new BancoDeHorasNãoEncontradoException("O banco de horas solicitado não pôde ser encontrado!");
    }

    public boolean verificarHorasTrabalhadadas(BancoDeHoras bancoDeHoras) {

        if (ValidaHoras.calcularHorasDeTrabalho(bancoDeHoras, funcionarioService, bancoDeHorasRepository) <= 50) {
            return true;
        } else {
            return false;
        }
    }

    public List<BancoDeHoras> exibirTodosBancosDeHoras() {

        return (List<BancoDeHoras>) bancoDeHorasRepository.findAll();
    }

    public List<BancoDeHoras> exibirHorasTrabalhadas(int id) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        BancoDeHoras bancoDeHoras = new BancoDeHoras();
        bancoDeHoras.setFuncionario(funcionario);

        return bancoDeHorasRepository.findAllByFuncionario(bancoDeHoras.getFuncionario());
    }

    public BancoDeHoras atualizarHorasTrabalhadasEntrada(int id, LocalDate data, BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        BancoDeHoras banco = bancoDeHorasRepository.findByDiaDoTrabalho(data);
        banco.setEntrada(bancoDeHoras.getEntrada());
        bancoDeHorasRepository.save(banco);

        return bancoDeHoras;
    }

    public BancoDeHoras atualizarHorasTrabalhadasSaida(int id, LocalDate data, BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        BancoDeHoras banco = bancoDeHorasRepository.findByDiaDoTrabalho(data);
        banco.setSaida(bancoDeHoras.getSaida());
        bancoDeHorasRepository.save(banco);

        return bancoDeHoras;
    }

    public void removerHorasFuncionario(int id) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
        funcionarioService.deletarHorasTrabalhadas(id);

        BancoDeHoras banco = new BancoDeHoras();
        banco.setFuncionario(funcionario);
        bancoDeHorasRepository.delete(banco);

    }

    public List<BancoDeHoras> horasExtrasTrabalhadas(LocalDate mes) {
        List<BancoDeHoras> listaDeHorasExtras = bancoDeHorasRepository.findAllByDiaDoTrabalho(mes);

        for (BancoDeHoras referencia : listaDeHorasExtras) {
            Funcionario funcionario = funcionarioService.buscarFuncionario(referencia.getFuncionario().getId());

            if (funcionario.getStatus().equals(Status.ATIVO)) {
                if (funcionario.getTotalHorasTrabalhadas() > 50) {
                    int horasExtras = referencia.getFuncionario().getTotalHorasTrabalhadas() - 50;
                    funcionario.setTotalHorasTrabalhadas(horasExtras);
                    referencia.setFuncionario(funcionario);
                    bancoDeHorasRepository.save(referencia);
                }
            }
            return listaDeHorasExtras;
        }

        throw new BancoDeHorasNãoEncontradoException("O banco de horas solicitado não pôde ser encontrado!");
    }

}
