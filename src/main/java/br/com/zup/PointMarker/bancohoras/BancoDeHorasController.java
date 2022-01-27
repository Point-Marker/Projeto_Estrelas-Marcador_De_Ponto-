package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs.AtualizarHorasTrabalhadasEntradaDTO;
import br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs.AtualizarHorasTrabalhadasSaidaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.zup.PointMarker.funcionario.FuncionarioService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/bancohoras")
public class BancoDeHorasController {
    private BancoDeHorasService bancoDeHorasService;
    private ModelMapper modelMapper;

    @Autowired
    public BancoDeHorasController(BancoDeHorasService bancoDeHorasService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.bancoDeHorasService = bancoDeHorasService;
    }

    @GetMapping("/{id}")
    public List<BancoDeHoras> exibirBancoDeHorasFuncionario(@PathVariable int id) {
        return bancoDeHorasService.exibirHorasTrabalhadas(id);
    }

    @PutMapping("/marcador/entrada/{idFuncionario}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarHorasTrabalhadasSaidaDTO atualizarHorasEntrada(@PathVariable int idFuncionario,
                                                                   @RequestParam @JsonFormat(pattern = "dd/mm/yyyy")
                                                                           LocalDateTime data,
                                                                   @RequestBody AtualizarHorasTrabalhadasEntradaDTO
                                                                           atualizarHorasTrabalhadasEntradaDTO) {

        BancoDeHoras banco = modelMapper.map(atualizarHorasTrabalhadasEntradaDTO, BancoDeHoras.class);
        bancoDeHorasService.atualizarHorasTrabalhadasEntrada(idFuncionario, data, banco);
        return modelMapper.map(banco, AtualizarHorasTrabalhadasSaidaDTO.class);
    }

    @PutMapping("/marcador/saida/{idFuncionario}")
    @ResponseStatus(HttpStatus.OK)
    public AtualizarHorasTrabalhadasSaidaDTO atualizarHorasSaida(@PathVariable int idFuncionario,
                                                                 @RequestParam @JsonFormat(pattern = "dd/mm/yyyy")
                                                                         LocalDateTime data,
                                                                 @RequestBody AtualizarHorasTrabalhadasEntradaDTO
                                                                         atualizarHorasTrabalhadasEntradaDTO) {

        BancoDeHoras banco = modelMapper.map(atualizarHorasTrabalhadasEntradaDTO, BancoDeHoras.class);
        bancoDeHorasService.atualizarHorasTrabalhadasSaida(idFuncionario, data, banco);
        return modelMapper.map(banco, AtualizarHorasTrabalhadasSaidaDTO.class);
    }
}
