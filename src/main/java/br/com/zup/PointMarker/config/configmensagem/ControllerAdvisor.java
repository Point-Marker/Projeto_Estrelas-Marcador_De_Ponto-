package br.com.zup.PointMarker.config.configmensagem;

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
    public MensagemDeErro manipularMensagegParaCargoNaoEncontrado() {

        return new MensagemDeErro("Este Cargo Não Está Cadastrado.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularMensagegParaDataDeNascimentoForaDoPadraoBrasileiro() {

        return new MensagemDeErro("Digite a sua data de nascimento, " +
                "de acordo com o exemplo a seguir: 23/06/2001.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro manipularMensagegParaCPFRepetido() {

        return new MensagemDeErro("Este CPF Já Está Cadastrado.");
    }

}
