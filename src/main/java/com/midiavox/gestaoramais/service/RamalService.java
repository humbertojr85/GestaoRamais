package com.midiavox.gestaoramais.service;

import com.midiavox.gestaoramais.model.Ramal;
import com.midiavox.gestaoramais.model.Usuario;
import com.midiavox.gestaoramais.repository.RamalRepository;
import com.midiavox.gestaoramais.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RamalService {

    private final RamalRepository ramalRepository;
    private final UsuarioRepository usuarioRepository;

    public RamalService(RamalRepository ramalRepository, UsuarioRepository usuarioRepository) {
        this.ramalRepository = ramalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarFaixaRamal(int de, int ate) {
        for (int i = de; i <= ate; i++) {
            String numero = String.valueOf(i);
            if (!ramalRepository.existsByNumero(numero)) {
                Ramal ramal = new Ramal();
                ramal.setNumero(numero);
                ramalRepository.save(ramal);
            }
        }
    }

    public List<Ramal> listarDisponiveis() {
        return ramalRepository.findByUsuarioLogadoIsNull();
    }

    public Ramal getRamalById(UUID id) {
        return ramalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ramal não encontrado"));
    }

    public void deletarRamal(UUID id) {
        if (ramalRepository.existsById(id)) {
            ramalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Ramal não encontrado");
        }
    }

    public List<Usuario> listarUsuariosLogados() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getRamal() != null)
                .toList();
    }

    public void logarRamal(UUID idUsuario, String numeroRamal) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getRamal() != null) {
            throw new RuntimeException("Usuário já está logado em um ramal");
        }

        Ramal ramal = ramalRepository.findByNumero(numeroRamal)
                .orElseThrow(() -> new RuntimeException("Ramal não encontrado"));

        if (ramal.isOcupado()) {
            throw new RuntimeException("Ramal já está em uso");
        }

        ramal.setUsuarioLogado(usuario);
        ramalRepository.save(ramal);
    }

    public void logoff(UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Ramal ramal = usuario.getRamal();
        if (ramal != null) {
            ramal.setUsuarioLogado(null);
            ramalRepository.save(ramal);
        }
    }
}
