package br.com.zup.PointMarker.bancohoras;


import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BancoDeHorasService {

    @Autowired
    private BancoDeHorasRepository bancoDeHorasRepository;

    @Autowired
    private FuncionarioService funcionarioService;



    public BancoDeHoras salvarHorasTrabalhadas(BancoDeHoras bancoDeHoras) {
        Funcionario funcionario = funcionarioService.buscarFuncionario(bancoDeHoras.getId_funcionario().getId());

            bancoDeHoras.setId_funcionario(funcionario);
            return bancoDeHorasRepository.save(bancoDeHoras);
    }


}
