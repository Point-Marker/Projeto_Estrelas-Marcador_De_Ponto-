package br.com.zup.PointMarker.exceptions;

import br.com.zup.PointMarker.bancohoras.BancoDeHoras;

public class BancoDeHorasNãoEncontradoException extends RuntimeException {
    public BancoDeHorasNãoEncontradoException(String message) {
        super(message);
    }

}
