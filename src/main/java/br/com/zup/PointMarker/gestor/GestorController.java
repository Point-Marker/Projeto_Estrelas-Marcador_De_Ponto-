package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarCargoDTO.AtualizarCargoEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarCargoDTO.AtualizarCargoSaidaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarSalarioDTO.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarSalarioDTO.AtualizarSalarioSaidaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarStatusDTO.AtualizarStatusEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.AtualizarStatusDTO.AtualizarStatusSaidaDTO;
import br.com.zup.PointMarker.funcionario.dtos.CadastroFuncionárioDTO.CadastroFuncionarioEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.CadastroFuncionárioDTO.CadastroFuncionarioSaidaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class GestorController {

    private GestorService gestorService;
    private GestorRepository gestorRepository;
    private ModelMapper modelMapper;

    @Autowired
    public GestorController(GestorService gestorService, GestorRepository gestorRepository, ModelMapper modelMapper) {
        this.gestorService = gestorService;
        this.gestorRepository = gestorRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CadastroFuncionarioSaidaDTO cadastrarFuncionario(@RequestBody @Valid CadastroFuncionarioEntradaDTO cadastroFuncionarioEntradaDTO) {

        Funcionario funcionario = modelMapper.map(cadastroFuncionarioEntradaDTO, Funcionario.class);
        gestorService.cadastrarFuncionario(funcionario);
        return modelMapper.map(funcionario, CadastroFuncionarioSaidaDTO.class);
    }

    @GetMapping
    @ResponseStatus
    public List<Funcionario> exibirFuncionariosAtivos(@RequestParam(required = false) Status status) {
        return gestorService.exibirTodosFuncionarios(status);
    }

    @GetMapping
    @ResponseStatus
    public Funcionario exibirFuncionarioUnico(@RequestParam(required = false) int id) {
        Funcionario idFuncionario = gestorService.exibirUmFuncionario(id);

        return idFuncionario;
    }

    @PutMapping("/salario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarSalarioSaidaDTO atualizarSalario(@PathVariable int id, @RequestBody AtualizarSalarioEntradaDTO atualizarSalario) {

        Funcionario funcionario = gestorService.exibirUmFuncionario(id);
        gestorService.atualizarSalario(id, atualizarSalario.getSalario());
        return modelMapper.map(funcionario, AtualizarSalarioSaidaDTO.class);
    }

    @PutMapping("/cargo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarCargoSaidaDTO atualizarCargo(@PathVariable int id, @RequestBody AtualizarCargoEntradaDTO atualizarCargoEntradaDTO) {

        Funcionario funcionario = gestorService.atualizarCargo(id, atualizarCargoEntradaDTO.getCargo());
        return modelMapper.map(funcionario, AtualizarCargoSaidaDTO.class);
    }

    @PutMapping("/status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarStatusSaidaDTO atualizarStatus(@PathVariable int id, @RequestBody AtualizarStatusEntradaDTO atualizarStatusEntradaDTO) {

        Status status = modelMapper.map(atualizarStatusEntradaDTO, Status.class);
        gestorService.atualizarStatus(id, status);
        return modelMapper.map(status, AtualizarStatusSaidaDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarFuncionario(@PathVariable int id) {
        gestorService.removerFuncionario(id);
    }

}
