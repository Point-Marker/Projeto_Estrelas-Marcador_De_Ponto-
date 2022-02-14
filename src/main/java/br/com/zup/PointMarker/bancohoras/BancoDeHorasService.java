package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.exceptions.BancoDeHorasNãoEncontradoException;
import br.com.zup.PointMarker.exceptions.HoraLimiteEntradaESaidaException;
import br.com.zup.PointMarker.exceptions.TotalDeHorasTrabalhadasUltrapassadaException;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        throw new TotalDeHorasTrabalhadasUltrapassadaException("Você excedeu seu total hora de trabalho neste mês.");
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

    public BancoDeHoras atualizarHorasTrabalhadas(BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionarioPeloCpf(bancoDeHoras.getFuncionario().getCpf());
        bancoDeHoras.setFuncionario(funcionario);
        if (ValidaHoras.validarHorasEntradaESaida(bancoDeHoras)) {
            Optional<BancoDeHoras> banco = bancoDeHorasRepository.findByDiaDoTrabalhoAndFuncionario(
                    bancoDeHoras.getDiaDoTrabalho(), bancoDeHoras.getFuncionario());
            if(banco.isPresent()){
                banco.get().setEntrada(bancoDeHoras.getEntrada());
                banco.get().setSaida(bancoDeHoras.getSaida());
                banco.get().setFuncionario(funcionario);
                bancoDeHorasRepository.save(banco.get());
                return banco.get();
            }
        throw new BancoDeHorasNãoEncontradoException("Este Funcionario Não Lançou Horas Neste Dia");
        }
        throw new HoraLimiteEntradaESaidaException("A hora registrada não pode ser antes das 08:00 ou depois das 22:00.");
    }

    public List<BancoDeHoras> horasExtrasTrabalhadas(LocalDate mes) {
        List<BancoDeHoras> listaDeHorasExtras = bancoDeHorasRepository.findAllByDiaDoTrabalho(mes);

        for (BancoDeHoras referencia : listaDeHorasExtras) {
            Funcionario funcionario = funcionarioService.buscarFuncionario(referencia.getFuncionario().getId());

            if (funcionario.getStatus().equals(Status.ATIVO)) {
                    referencia.setFuncionario(funcionario);
                    bancoDeHorasRepository.save(referencia);
            }
            return listaDeHorasExtras;
        }

        throw new BancoDeHorasNãoEncontradoException("O banco de horas solicitado não pôde ser encontrado!");
    }

    public void removerHorasFuncionario(int id) {
        bancoDeHorasRepository.deleteById(id);
    }

}
