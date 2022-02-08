package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.funcionario.dtos.FuncionarioDetailsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    private ModelMapper modelMapper;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService, ModelMapper modelMapper) {
        this.funcionarioService = funcionarioService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public FuncionarioDetailsDTO exibirFuncionarioId(@PathVariable int id) {
        Funcionario idFuncionario = funcionarioService.buscarFuncionario(id);

        return modelMapper.map(idFuncionario, FuncionarioDetailsDTO.class);
    }

}