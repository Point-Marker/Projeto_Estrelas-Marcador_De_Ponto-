package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.bancohoras.dtos.ResumoDTO.BancoDeHorasResumoDTO;
import br.com.zup.PointMarker.funcionario.Funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.zup.PointMarker.funcionario.FuncionarioService;
import br.com.zup.PointMarker.funcionario.dtos.ResumoDTO.ResumoFuncionarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {

    private BancoDeHorasService bancoDeHorasService;

    private ModelMapper modelMapper;


    @Autowired
    public BancoDeHorasController(BancoDeHorasService bancoDeHorasService, ModelMapper modelMapper) {
        this.bancoDeHorasService = bancoDeHorasService;
        this.modelMapper = modelMapper;
      }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BancoDeHorasResumoDTO cadastrarHorasTrabalhadas(@RequestBody BancoDeHoras bancoDeHoras) {
        BancoDeHoras bancoDeHorasSalvo = bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHoras);
        Funcionario funcionario = bancoDeHorasSalvo.getId_funcionario();
        ResumoFuncionarioDTO resumoFuncionarioDTO = modelMapper.map(funcionario, ResumoFuncionarioDTO.class);

        BancoDeHorasResumoDTO resumoDTO = modelMapper.map(bancoDeHorasSalvo, BancoDeHorasResumoDTO.class);
        resumoDTO.setFuncionario(resumoFuncionarioDTO);

        return resumoDTO;
    }

    @GetMapping("/{id}")
    public List<BancoDeHoras> exibirBancoDeHorasFuncionario(@PathVariable int id){
        return bancoDeHorasService.exibirHorasTrabalhadas(id);
    }

}
