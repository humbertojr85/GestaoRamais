package com.midiavox.gestaoramaisdesktop.model;

public class RamalFaixa {
    private int inicio;
    private int fim;

    public RamalFaixa(int inicio, int fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFim() {
        return fim;
    }
}
