package br.com.zup.PointMarker.exceptions;

public class FuncionarioComStatusInativo extends RuntimeException {
    public FuncionarioComStatusInativo(String message) {
        super(message);
    }
}
