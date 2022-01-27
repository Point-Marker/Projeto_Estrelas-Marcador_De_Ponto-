package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.dtos.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioSaidaDTO cadastrarFuncionario(@RequestBody @Valid EntradaFuncionarioDTO entradaFuncionarioDTO) {
        Funcionario funcionario = modelMapper.map(entradaFuncionarioDTO, Funcionario.class);
        funcionarioService.salvarFuncionario(funcionario);
        return modelMapper.map(funcionario, FuncionarioSaidaDTO.class);
    }

    @GetMapping
    public List<Funcionario> exibirFuncionariosAtivos(@RequestParam(required = false) Status status) {
        return funcionarioService.exibirFuncionarios(status);
    }


    @PutMapping("/salario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarSalarioSaidaDTO atualizarSalario(@PathVariable int id, @RequestBody AtualizarSalarioEntradaDTO atualizarSalarioEntradaDTO) {

        Funcionario funcionario = funcionarioService.atualizarSalario(id, atualizarSalarioEntradaDTO.getSalario());

        return modelMapper.map(funcionario, AtualizarSalarioSaidaDTO.class);
    }

    @PutMapping("/cargo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarCargoSaidaDTO atualizarCargo(@PathVariable int id, @RequestBody AtualizarCargoEntradaDTO atualizarCargoEntradaDTO) {

        Funcionario funcionario = funcionarioService.atualizarCargo(id, atualizarCargoEntradaDTO.getCargo());

        return modelMapper.map(funcionario, AtualizarCargoSaidaDTO.class);
    }

    @PutMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarStatusSaidaDTO atualizarStatus(@PathVariable int id, @RequestBody AtualizarStatusEntradaDTO atualizarStatusEntradaDTO) {

        Funcionario funcionario = funcionarioService.atualizarStatus(id, atualizarStatusEntradaDTO.getStatus());

        return modelMapper.map(funcionario, AtualizarStatusSaidaDTO.class);
    }
}