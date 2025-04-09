package com.midiavox.gestaoramais.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Ramal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String numero;

    @OneToOne
    private Usuario usuarioLogado;

    public boolean isOcupado() {
        return usuarioLogado != null;
    }
}