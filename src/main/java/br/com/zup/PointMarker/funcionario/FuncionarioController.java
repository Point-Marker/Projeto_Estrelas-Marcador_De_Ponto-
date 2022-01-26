package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> exibirFuncionariosAtivos(@RequestParam (required = false) Status status) {
        return funcionarioService.exibirFuncionarios(status);
    }

}
