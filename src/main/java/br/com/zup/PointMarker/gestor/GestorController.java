package br.com.zup.PointMarker.gestor;

import br.com.zup.PointMarker.enums.Status;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.CadastroFuncionárioDTO.CadastroFuncionarioEntradaDTO;
import br.com.zup.PointMarker.funcionario.dtos.CadastroFuncionárioDTO.CadastroFuncionarioSaidaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Funcionario> exibirFuncionariosAtivos(@RequestParam(required = false) Status status) {
        return gestorService.exibirTodosFuncionarios(status);
    }
}
