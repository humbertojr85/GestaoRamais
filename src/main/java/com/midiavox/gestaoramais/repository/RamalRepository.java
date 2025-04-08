package com.midiavox.gestaoramais.repository;

import com.midiavox.gestaoramais.model.Ramal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RamalRepository extends JpaRepository<Ramal, UUID> {
    Optional<Ramal> findByNumero(String numero);
}
