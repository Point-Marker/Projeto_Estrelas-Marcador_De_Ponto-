package br.com.zup.PointMarker.funcionario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @ResponseStatus
    public Funcionario exibirFuncionarioId(@RequestParam(required = false) int id) {
        Funcionario idFuncionario = funcionarioService.buscarFuncionario(id);

        return idFuncionario;
    }

}