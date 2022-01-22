package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.funcionario.dtos.EntradaFuncionarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarFuncionario(@RequestBody EntradaFuncionarioDTO entradaFuncionarioDTO) {
        Funcionario funcionario = modelMapper.map(entradaFuncionarioDTO, Funcionario.class);
        funcionarioService.salvarFuncionario(funcionario);
    }
}
