package com.midiavox.gestaoramaisdesktop.model;

public class LogarPayload {
    private String idUsuario;
    private String idRamal;

    public LogarPayload(String idUsuario, String idRamal) {
        this.idUsuario = idUsuario;
        this.idRamal = idRamal;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdRamal() {
        return idRamal;
    }
}
