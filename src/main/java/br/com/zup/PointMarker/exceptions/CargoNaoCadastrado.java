package br.com.zup.PointMarker.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CargoNaoCadastrado extends RuntimeException {
    public CargoNaoCadastrado(String message) {
        super(message);
    }
}
