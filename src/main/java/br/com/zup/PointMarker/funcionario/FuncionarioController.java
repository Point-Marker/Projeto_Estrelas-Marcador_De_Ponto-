package br.com.zup.PointMarker.funcionario;

import br.com.zup.PointMarker.dtos.AtualizarSalarioEntradaDTO;
import br.com.zup.PointMarker.dtos.AtualizarSalarioSaidaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @PutMapping("/cargo/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarSalarioSaidaDTO atualizarSalarioDTO (@PathVariable int id, @RequestBody AtualizarSalarioEntradaDTO atualizarSalarioEntradaDTO){

    }
}
