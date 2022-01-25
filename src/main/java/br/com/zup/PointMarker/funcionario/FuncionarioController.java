package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.cargo.Cargo;
import br.com.zup.PointMarker.dtos.AtualizarCargoEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarCargoSaidaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioSaidaDTO;
import br.com.zup.PointMarker.dtos.AtualizarStatusEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarStatusSaidaDTO;
import br.com.zup.PointMarker.enums.Status;
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
    public AtualizarSalarioSaidaDTO atualizarSalario(@PathVariable int id, @RequestBody AtualizarSalarioEntradaDTO atualizarSalarioEntradaDTO) {

        Funcionario salario = modelMapper.map(atualizarSalarioEntradaDTO, Funcionario.class);

        funcionarioService.atualizarSalario(id, salario.getSalario());

        return modelMapper.map(salario, AtualizarSalarioSaidaDTO.class);
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

        Status status = modelMapper.map(atualizarStatusEntradaDTO, Status.class);

        Funcionario funcionario = funcionarioService.atualizarStatus(id, status);

        return modelMapper.map(status, AtualizarStatusSaidaDTO.class);
    }
}
