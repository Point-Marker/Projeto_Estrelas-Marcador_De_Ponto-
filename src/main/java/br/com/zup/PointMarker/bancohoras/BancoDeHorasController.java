package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.bancohoras.dtos.BancoDeHorasResumoDTO;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.ResumoFuncionarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {

    @Autowired
    private BancoDeHorasService horasService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public BancoDeHorasResumoDTO cadastrarHorasTrabalhadas(@RequestBody BancoDeHoras bancoDeHoras) {
        BancoDeHoras bancoDeHorasSalvo = horasService.salvarHorasTrabalhadas(bancoDeHoras);
        Funcionario funcionario = bancoDeHorasSalvo.getId_funcionario();
        ResumoFuncionarioDTO resumoFuncionarioDTO = modelMapper.map(funcionario, ResumoFuncionarioDTO.class);

        BancoDeHorasResumoDTO resumoDTO = modelMapper.map(bancoDeHorasSalvo, BancoDeHorasResumoDTO.class);
        resumoDTO.setFuncionario(resumoFuncionarioDTO);

        return resumoDTO;
    }
}
