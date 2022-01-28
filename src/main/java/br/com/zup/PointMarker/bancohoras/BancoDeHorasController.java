package br.com.zup.PointMarker.bancohoras;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.zup.PointMarker.funcionario.FuncionarioService;
import org.modelmapper.ModelMapper;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {
    private FuncionarioService funcionarioService;
    private ModelMapper modelMapper;
    private BancoDeHorasService bancoDeHorasService;

    @Autowired
    public BancoDeHorasController(FuncionarioService funcionarioService, ModelMapper modelMapper, BancoDeHorasService bancoDeHorasService) {
        this.funcionarioService = funcionarioService;
        this.modelMapper = modelMapper;
        this.bancoDeHorasService = bancoDeHorasService;
      }
  
    @GetMapping("/{id}")
    public List<BancoDeHoras> exibirBancoDeHorasFuncionario(@PathVariable int id){
        return bancoDeHorasService.exibirHorasTrabalhadas(id);
    }

}
