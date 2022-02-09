package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.funcionario.dtos.FuncionarioDetailsDTO;
import br.com.zup.PointMarker.usuario.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    private ModelMapper modelMapper;

    private UsuarioService usuarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService, ModelMapper modelMapper, UsuarioService usuarioService) {
        this.funcionarioService = funcionarioService;
        this.modelMapper = modelMapper;
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public FuncionarioDetailsDTO exibirFuncionarioId() {
        Funcionario idFuncionario = funcionarioService.buscarFuncionario(usuarioService.pegarId());

        return modelMapper.map(idFuncionario, FuncionarioDetailsDTO.class);
    }

}