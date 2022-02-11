package br.com.zup.PointMarker.bancohoras;

import br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs.AtualizarHorasTrabalhadasEntradaDTO;
import br.com.zup.PointMarker.bancohoras.dtos.AtualizarHorasTrabalhadasDTOs.AtualizarHorasTrabalhadasSaidaDTO;
import br.com.zup.PointMarker.bancohoras.dtos.ResumoSaidaDTO.BancoDeHorasResumoDTO;
import br.com.zup.PointMarker.bancohoras.dtos.cadastrodebancodehorasdto.CadastroEntradaBancoDeHorasDTO;
import br.com.zup.PointMarker.funcionario.Funcionario;
import br.com.zup.PointMarker.funcionario.dtos.ResumoDTO.ResumoFuncionarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public BancoDeHorasResumoDTO cadastrarHorasTrabalhadas(@RequestBody CadastroEntradaBancoDeHorasDTO entradaBanco) {

        BancoDeHoras bancoDeHorasASerSalvo = modelMapper.map(entradaBanco, BancoDeHoras.class);
        BancoDeHoras bancoDeHorasSalvo = bancoDeHorasService.salvarHorasTrabalhadas(bancoDeHorasASerSalvo);
        Funcionario funcionario = bancoDeHorasSalvo.getFuncionario();
        ResumoFuncionarioDTO resumoFuncionarioDTO = modelMapper.map(funcionario, ResumoFuncionarioDTO.class);

        BancoDeHorasResumoDTO resumoBancoDTO = modelMapper.map(bancoDeHorasSalvo, BancoDeHorasResumoDTO.class);
        resumoBancoDTO.setFuncionario(resumoFuncionarioDTO);

        return resumoBancoDTO;
    }

    @GetMapping
    public List<BancoDeHorasResumoDTO> exibirTodosBancosDeHoras() {
        List<BancoDeHoras> todosBancosDeHoras = bancoDeHorasService.exibirTodosBancosDeHoras();
        List<BancoDeHorasResumoDTO> listaResumoBancoDeHoras = new ArrayList<>();

        for (BancoDeHoras refencia : todosBancosDeHoras) {
            BancoDeHorasResumoDTO bancoDeHorasResumoDTO = modelMapper.map(refencia, BancoDeHorasResumoDTO.class);
            listaResumoBancoDeHoras.add(bancoDeHorasResumoDTO);
        }

        return listaResumoBancoDeHoras;
    }

    @GetMapping("/{id}")
    public List<BancoDeHoras> exibirBancoDeHorasFuncionario(@PathVariable int id) {
        return bancoDeHorasService.exibirHorasTrabalhadas(id);
    }

    @GetMapping("/horasextras")
    public List<BancoDeHoras> exibirHorasExtrasTrabalhadas(@RequestParam LocalDate mesDeFiltro) {
        return bancoDeHorasService.horasExtrasTrabalhadas(mesDeFiltro);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AtualizarHorasTrabalhadasSaidaDTO atualizarHorasEntrada(@RequestBody AtualizarHorasTrabalhadasEntradaDTO
                                                                           atualizarHorasTrabalhadas) {

        BancoDeHoras bancoComHoraASerAtualizada = modelMapper.map(atualizarHorasTrabalhadas, BancoDeHoras.class);
        BancoDeHoras bancoDeHorasComHorasAtualizada = bancoDeHorasService.atualizarHorasTrabalhadas(
                bancoComHoraASerAtualizada);
        return modelMapper.map(bancoDeHorasComHorasAtualizada, AtualizarHorasTrabalhadasSaidaDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarHorasFuncionario(@PathVariable int id) {
        bancoDeHorasService.removerHorasFuncionario(id);
    }

}
