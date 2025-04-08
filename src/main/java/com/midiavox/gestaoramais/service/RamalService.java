package com.midiavox.gestaoramais.service;

import com.midiavox.gestaoramais.model.Ramal;
import com.midiavox.gestaoramais.model.Usuario;
import com.midiavox.gestaoramais.repository.RamalRepository;
import com.midiavox.gestaoramais.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RamalService {

    private final RamalRepository ramalRepository;
    private final UsuarioRepository usuarioRepository;

    public RamalService(RamalRepository ramalRepository, UsuarioRepository usuarioRepository) {
        this.ramalRepository = ramalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Ramal> listarTodos() {
        return ramalRepository.findAll();
    }

    public Ramal criarRamal(String numero) {
        Ramal ramal = new Ramal();
        ramal.setNumero(numero);
        return ramalRepository.save(ramal);
    }

    public void logarUsuarioEmRamal(UUID usuarioId, UUID ramalId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Ramal ramal = ramalRepository.findById(ramalId)
                .orElseThrow(() -> new RuntimeException("Ramal não encontrado"));

        // Verifica se o usuário já está logado em algum ramal
        boolean usuarioJaLogado = ramalRepository.findAll().stream()
                .anyMatch(r -> r.getUsuarioLogado() != null && r.getUsuarioLogado().getId().equals(usuarioId));

        if (usuarioJaLogado) {
            throw new RuntimeException("Usuário já está logado em outro ramal");
        }

        if (ramal.getUsuarioLogado() != null) {
            throw new RuntimeException("Ramal já está ocupado por outro usuário");
        }

        ramal.setUsuarioLogado(usuario);
        ramalRepository.save(ramal);
    }

    public void deslogarUsuario(UUID usuarioId) {
        List<Ramal> ramais = ramalRepository.findAll();

        for (Ramal r : ramais) {
            if (r.getUsuarioLogado() != null && r.getUsuarioLogado().getId().equals(usuarioId)) {
                r.setUsuarioLogado(null);
                ramalRepository.save(r);
                break;
            }
        }
    }
}
