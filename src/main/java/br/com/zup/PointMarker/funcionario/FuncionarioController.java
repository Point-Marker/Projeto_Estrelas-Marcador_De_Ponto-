package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.dtos.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioSaidaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

    @PutMapping("/salario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarSalarioSaidaDTO atualizarSalarioDTO (@PathVariable int id, @RequestBody AtualizarSalarioEntradaDTO atualizarSalarioEntradaDTO){

        Funcionario funcionario = funcionarioService.buscarFuncionario(id);
         if (funcionario != null){
            funcionario.setSalario(atualizarSalarioEntradaDTO.getSalario());
         }
         return modelMapper.map(funcionario, AtualizarSalarioSaidaDTO.class);
    }
}
