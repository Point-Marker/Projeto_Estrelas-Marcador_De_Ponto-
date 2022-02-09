package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.BancoDeHorasNãoEncontradoException;
import br.com.zup.PointMarker.exceptions.CargaHorariaUltrapassadaException;
import br.com.zup.PointMarker.exceptions.HoraLimiteEntradaESaidaException;
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
        throw new CargaHorariaUltrapassadaException("Você excedeu sua carga horária de trabalho!");
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

    public BancoDeHoras atualizarHorasTrabalhadas(int id, LocalDate data, BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
        BancoDeHoras banco = bancoDeHorasRepository.findByDiaDoTrabalho(data);

        if (ValidaHoras.validarHorasEntradaESaida(bancoDeHoras)) {

            banco.setEntrada(bancoDeHoras.getEntrada());
            banco.setSaida(bancoDeHoras.getSaida());
            bancoDeHorasRepository.save(banco);

            return bancoDeHoras;
        }
        throw new HoraLimiteEntradaESaidaException("A hora registrada não pode ser antes das 08:00 ou depois das 22:00.");
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

    public void removerHorasFuncionario(int id) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        List<BancoDeHoras> bancoDeHorasList = bancoDeHorasRepository.findAllByFuncionario(funcionario);

        for (BancoDeHoras bancoReferencia : bancoDeHorasList) {
            bancoDeHorasRepository.delete(bancoReferencia);
        }
    }

}
