package com.midiavox.gestaoramais.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "usuario_logado_id")
    @JsonManagedReference
    private Usuario usuarioLogado;

    public boolean isOcupado() {
        return usuarioLogado != null;
    }
}