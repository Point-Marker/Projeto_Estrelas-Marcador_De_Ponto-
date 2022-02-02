package br.com.zup.PointMarker.funcionario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{id}'")
    public Funcionario exibirFuncionarioId(int id) {
        Funcionario idFuncionario = funcionarioService.buscarFuncionario(id);

        return idFuncionario;
    }

}