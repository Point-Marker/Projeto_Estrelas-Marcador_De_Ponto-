package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {

    private FuncionarioService funcionarioService;

    private ModelMapper modelMapper;

    @Autowired
    public BancoDeHorasController(FuncionarioService funcionarioService, ModelMapper modelMapper) {
        this.funcionarioService = funcionarioService;
        this.modelMapper = modelMapper;
    }
}
