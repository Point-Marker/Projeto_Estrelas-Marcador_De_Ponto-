package br.com.zup.PointMarker.bancohoras;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {

    @Autowired
    private BancoDeHorasService bancoDeHorasService;

    @GetMapping("/{id}")
    public List<BancoDeHoras> exibirBancoDeHorasFuncionario(@PathVariable int id){
        return bancoDeHorasService.exibirHorasTrabalhadas(id);
    }

}
