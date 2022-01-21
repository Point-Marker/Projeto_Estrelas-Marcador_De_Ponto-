package br.com.zup.PointMarker.enums;

public enum Status {
    ATIVO(true), INATIVO(false);

    private boolean statusFuncionario;

    Status(boolean statusFuncionario) {
        this.statusFuncionario = statusFuncionario;
    }

    public boolean getStatusAFuncionario() {
        return statusFuncionario;
    }
}
