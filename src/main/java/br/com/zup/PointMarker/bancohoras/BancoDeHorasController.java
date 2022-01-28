package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.bancohoras.dtos.ResumoSaidaDTO.BancoDeHorasResumoDTO;
import br.com.zup.PointMarker.bancohoras.dtos.cadastrodebancodehorasdto.CadastroEntradaBancoDeHorasDTO;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.FuncionarioService;
import br.com.zup.PointMarker.funcionario.dtos.ResumoDTO.ResumoFuncionarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {

    private BancoDeHorasService horasService;

    private ModelMapper modelMapper;

    private FuncionarioService funcionarioService;


    @Autowired
    public BancoDeHorasController(FuncionarioService funcionarioService, ModelMapper modelMapper, BancoDeHorasService horasService) {
        this.funcionarioService = funcionarioService;
        this.modelMapper = modelMapper;
        this.horasService = horasService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BancoDeHorasResumoDTO cadastrarHorasTrabalhadas(@RequestBody CadastroEntradaBancoDeHorasDTO
                                                                   cadastroEntrada) {
        BancoDeHoras bancoDeHorasSalvo = modelMapper.map(cadastroEntrada, BancoDeHoras.class);
        horasService.salvarHorasTrabalhadas(bancoDeHorasSalvo);

        Funcionario funcionario = bancoDeHorasSalvo.getFuncionario();
        ResumoFuncionarioDTO resumoFuncionarioDTO = modelMapper.map(funcionario, ResumoFuncionarioDTO.class);

        BancoDeHorasResumoDTO resumoBancoDTO = modelMapper.map(bancoDeHorasSalvo, BancoDeHorasResumoDTO.class);
        resumoBancoDTO.setFuncionario(resumoFuncionarioDTO);

        return resumoBancoDTO;
    }
}
