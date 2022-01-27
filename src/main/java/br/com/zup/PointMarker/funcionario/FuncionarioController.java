package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.funcionario.dtos.EntradaFuncionarioDTO;
import br.com.zup.PointMarker.funcionario.dtos.FuncionarioSaidaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioSaidaDTO cadastrarFuncionario(@RequestBody @Valid EntradaFuncionarioDTO entradaFuncionarioDTO) {
        Funcionario funcionario = modelMapper.map(entradaFuncionarioDTO, Funcionario.class);
        funcionarioService.salvarFuncionario(funcionario);
        FuncionarioSaidaDTO funcionarioSaidaDTO = modelMapper.map(funcionario, FuncionarioSaidaDTO.class);
        return funcionarioSaidaDTO;
    }
}
