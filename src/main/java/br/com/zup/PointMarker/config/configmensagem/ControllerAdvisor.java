package br.com.zup.PointMarker.config.configmensagem;

import br.com.zup.PointMarker.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ErroValidacao> manipularErrosValidacao(MethodArgumentNotValidException exception) {
        List<ErroValidacao> erros = new ArrayList<>();

        for (FieldError fieldError : exception.getFieldErrors()) {
            ErroValidacao erroValidacao = new ErroValidacao(fieldError.getField(),
                    fieldError.getDefaultMessage());
            erros.add(erroValidacao);
        }
        return erros;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroValidacao manipularMensagemParaCargoNaoEncontrado(NoSuchElementException exception) {
        return new ErroValidacao(exception.getLocalizedMessage(), exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemParaDataDeNascimentoForaDoPadraoBrasileiro() {
        return new MensagemDeErro("de acordo com o seguinte formato: 23/06/2001 -> dd/MM/aaaa.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemParaCPFRepetido() {
        return new MensagemDeErro("Este CPF J?? Est?? Cadastrado.");
    }

    @ExceptionHandler(FuncionarioComStatusInativoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaFuncionarioComStatusInativo(FuncionarioComStatusInativoException
                                                                                             statusInativo) {
        return new MensagemDeErro(statusInativo.getMessage());
    }

    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularMensagemDeErroParaFuncionarioNaoEncontrado(FuncionarioNaoEncontradoException
                                                                                    funcionarioNaoEncontrado) {
        return new MensagemDeErro(funcionarioNaoEncontrado.getMessage());
    }

    @ExceptionHandler(AumentoDeSalarioInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaAumentoDeSalarioInvalido(AumentoDeSalarioInvalidoException
                                                                                          salarioInvalido) {
        return new MensagemDeErro(salarioInvalido.getMessage());
    }

    @ExceptionHandler(HoraLimiteEntradaESaidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaHoraLimite(HoraLimiteEntradaESaidaException
                                                                            horaLimite) {
        return new MensagemDeErro(horaLimite.getMessage());
    }

    @ExceptionHandler(HorarioInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaHorarioInvalido(HorarioInvalidoException horarioInvalido) {
        return new MensagemDeErro(horarioInvalido.getMessage());
    }

    @ExceptionHandler(CargoJaCadastradoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemDeErro manipularMensagemDeErroParaCargoJaCadastrado(CargoJaCadastradoException
                                                                                   cargoJaCadastradoException) {
        return new MensagemDeErro(cargoJaCadastradoException.getMessage());
    }

    @ExceptionHandler(CargaHorariaUltrapassadaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaCargaHorariaUltrapassadaException(CargaHorariaUltrapassadaException
                                                                exceptionCargaHoraria) {
        return new MensagemDeErro(exceptionCargaHoraria.getMessage());
    }

    @ExceptionHandler(TotalDeHorasUltrapassadaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaTotalDeHorasUltrapassada(TotalDeHorasUltrapassadaException
                                                                                          totalDeHorasUltrapassada) {
        return new MensagemDeErro(totalDeHorasUltrapassada.getMessage());
    }

    @ExceptionHandler(HoraJaInseridaNoSistemaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemDeErro manipularMensagemDeErroParaHoraJaInseridaNoSistema(HoraJaInseridaNoSistemaException
                                                          horaJaInseridaNoSistemaException) {
        return new MensagemDeErro(horaJaInseridaNoSistemaException.getMessage());
    }

    @ExceptionHandler(BancoDeHorasN??oEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularMensagemDeErroParaBancoDeHorasNaoEncontrado(BancoDeHorasN??oEncontradoException
                                                            bancoDeHorasN??oEncontradoException) {
        return new MensagemDeErro(bancoDeHorasN??oEncontradoException.getMessage());
    }

    @ExceptionHandler(LimiteAumentoSalarioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaLimiteAumentoSalario(LimiteAumentoSalarioException
                                                                                      limiteAumentoSalarioException) {
        return new MensagemDeErro(limiteAumentoSalarioException.getMessage());
    }

    @ExceptionHandler(MaisDeCinquentaHorasTrabalhadasException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaMaisDeCinquentaHoras(MaisDeCinquentaHorasTrabalhadasException
                                                       maisDeCinquentaHorasTrabalhadasException) {
        return new MensagemDeErro(maisDeCinquentaHorasTrabalhadasException.getMessage());
    }

    @ExceptionHandler(StatusInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemDeErro manipularMensagemDeErroParaStatusInvalido(StatusInvalidoException statusInvalidoException) {
        return new MensagemDeErro(statusInvalidoException.getMessage());
    }

    @ExceptionHandler(CargoNaoCadastrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularMensagemDeErroParaCargoNaoEncontrado(CargoNaoCadastrado cargoNaoCadastrado) {
        return new MensagemDeErro(cargoNaoCadastrado.getMessage());
    }

}
