package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.bancohoras.dtos.ResumoSaidaDTO.BancoDeHorasResumoDTO;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.ResumoDTO.ResumoFuncionarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs.AtualizarHorasTrabalhadasEntradaDTO;
import br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs.AtualizarHorasTrabalhadasSaidaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;

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
        Funcionario funcionario = bancoDeHorasSalvo.getFuncionario();
        ResumoFuncionarioDTO resumoFuncionarioDTO = modelMapper.map(funcionario, ResumoFuncionarioDTO.class);

        BancoDeHorasResumoDTO resumoBancoDTO = modelMapper.map(bancoDeHorasSalvo, BancoDeHorasResumoDTO.class);
        resumoBancoDTO.setFuncionario(resumoFuncionarioDTO);

        return resumoBancoDTO;
    }

    @GetMapping("/{id}")
    public List<BancoDeHoras> exibirBancoDeHorasFuncionario(@PathVariable int id) {
        return bancoDeHorasService.exibirHorasTrabalhadas(id);
    }

    @GetMapping("/horasextras")
    public List<BancoDeHoras> exibirHorasExtrasTrabalhadas(@RequestParam LocalDate mesDeFiltro) {
        return bancoDeHorasService.horasExtrasTrabalhadas(mesDeFiltro);
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarHorasFuncionario(@PathVariable int id) {
        bancoDeHorasService.removerHorasFuncionario(id);
    }
}
