package com.midiavox.gestaoramaisdesktop.model;

public class Ramal {
    private String id;
    private String numero;
    private boolean disponivel;

    public String getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Ramal " + numero; // qualquer atributo que queira exibir
    }
}
