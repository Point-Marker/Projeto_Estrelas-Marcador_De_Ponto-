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
    public ErroValidacao manipularMensagegParaCargoNaoEncontrado(NoSuchElementException exception) {
        return new ErroValidacao(exception.getLocalizedMessage(), exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagegParaDataDeNascimentoForaDoPadraoBrasileiro() {
        return new MensagemDeErro("de acordo com o seguinte formato: 23/06/2001 -> dd/MM/AAAA.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagegParaCPFRepetido() {
        return new MensagemDeErro("Este CPF Já Está Cadastrado.");
    }

    @ExceptionHandler(FuncionarioComStatusInativoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaFuncionarioComStatusInativ(FuncionarioComStatusInativoException statusInativo) {
        return new MensagemDeErro(statusInativo.getMessage());
    }

    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularMensagemDeErroParaFuncionarioComStatusInativ(FuncionarioNaoEncontradoException naoEncontrado) {
        return new MensagemDeErro(naoEncontrado.getMessage());
    }

    @ExceptionHandler(AumentoDeSalarioInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaFuncionarioComStatusInativ(AumentoDeSalarioInvalidoException valorInvalido) {
        return new MensagemDeErro(valorInvalido.getMessage());
    }

    @ExceptionHandler(HorarioInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro manipularMensagemDeErroParaHorarioInvalido(HorarioInvalidoException horarioInvalido) {
        return new MensagemDeErro(horarioInvalido.getMessage());
    }

    @ExceptionHandler(CargoJaCadastradoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemDeErro cargoJaCadastrado(CargoJaCadastradoException cargoJaCadastradoException) {
        return new MensagemDeErro(cargoJaCadastradoException.getMessage());
    }

    @ExceptionHandler(CargoHorariaUltrapassadaException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MensagemDeErro voceExcedeuAsHorasTrabalhadas(CargoHorariaUltrapassadaException
                                                                    voceExcedeuAsHorasTrabalhadasException) {
        return new MensagemDeErro(voceExcedeuAsHorasTrabalhadasException.getMessage());
    }

    @ExceptionHandler(ASuaCargaHorariaException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MensagemDeErro aSuaCargaHoraria(ASuaCargaHorariaException aSuaCargaHorariaException) {
        return new MensagemDeErro(aSuaCargaHorariaException.getMessage());
    }

    @ExceptionHandler(HoraJaInseridaNoSistemaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MensagemDeErro horaJaInseridaNoSistema(HoraJaInseridaNoSistemaException
                                                              horaJaInseridaNoSistemaException){
        return new MensagemDeErro(horaJaInseridaNoSistemaException.getMessage());
    }

    @ExceptionHandler(BancoDeHorasNãoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro bancoDeHorasNaoEncontrado(BancoDeHorasNãoEncontradoException
                                                                bancoDeHorasNãoEncontradoException) {
        return new MensagemDeErro(bancoDeHorasNãoEncontradoException.getMessage());
    }

    @ExceptionHandler(LimiteAumentoSalarioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro limiteAumentoSalario(LimiteAumentoSalarioException limiteAumentoSalarioException){
        return new MensagemDeErro(limiteAumentoSalarioException.getMessage());
    }

    @ExceptionHandler(MaisDeCinquentaHorasTrabalhadasException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MensagemDeErro maisDeCinquentaHoras(MaisDeCinquentaHorasTrabalhadasException
                                                           maisDeCinquentaHorasTrabalhadasException){
        return new MensagemDeErro(maisDeCinquentaHorasTrabalhadasException.getMessage());
    }

}
