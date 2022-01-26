package br.com.zup.PointMarker.exceptions;

public class FuncionarioNaoEncontradoException extends RuntimeException{
    public FuncionarioNaoEncontradoException(String message){
        super (message);
    }
}
