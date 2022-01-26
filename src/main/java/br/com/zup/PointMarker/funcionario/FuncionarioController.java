package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.dtos.AtualizarCargoEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarCargoSaidaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioSaidaDTO;
import br.com.zup.PointMarker.dtos.AtualizarStatusEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarStatusSaidaDTO;
import br.com.zup.PointMarker.funcionario.dtos.EntradaFuncionarioDTO;
import br.com.zup.PointMarker.funcionario.dtos.FuncionarioSaidaDTO;
import br.com.zup.PointMarker.enums.Status;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.Optional;

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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioSaidaDTO cadastrarFuncionario(@RequestBody @Valid EntradaFuncionarioDTO entradaFuncionarioDTO) {
        Funcionario funcionario = modelMapper.map(entradaFuncionarioDTO, Funcionario.class);
        funcionarioService.salvarFuncionario(funcionario);
        return modelMapper.map(funcionario, FuncionarioSaidaDTO.class);
    }

     @GetMapping
     public List<Funcionario> exibirFuncionariosAtivos(@RequestParam (required = false) Status status) {
        return funcionarioService.exibirFuncionarios(status);
    }

  
    @PutMapping("/salario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarSalarioSaidaDTO atualizarSalario(@PathVariable int id, @RequestBody AtualizarSalarioEntradaDTO atualizarSalarioEntradaDTO) {

        Funcionario funcionario = funcionarioService.buscarFuncionario(id);

        funcionarioService.atualizarSalario(id, atualizarSalarioEntradaDTO.getSalario());

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
    public AtualizarStatusSaidaDTO atualizarStatus(@PathVariable int id, @RequestBody AtualizarStatusEntradaDTO atualizarStatusEntradaDTO){

        Status status = modelMapper.map(atualizarStatusEntradaDTO, Status.class);

        funcionarioService.atualizarStatus(id, status);

        return modelMapper.map(status, AtualizarStatusSaidaDTO.class);
    }
}