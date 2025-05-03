package com.midiavox.gestaoramais.repository;

import com.midiavox.gestaoramais.model.Ramal;
import com.midiavox.gestaoramais.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface RamalRepository extends JpaRepository<Ramal, UUID> {
    Optional<Ramal> findByNumero(String numero);
    boolean existsByNumero(String numero);
    List<Ramal> findByUsuarioLogadoIsNull();
    Optional<Ramal> findByUsuarioLogado(Usuario usuarioLogado);
}
