package com.midiavox.gestaoramais.service;

import com.midiavox.gestaoramais.model.Usuario;
import com.midiavox.gestaoramais.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(UUID.fromString(id));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void atualizarUsuario(String id, Usuario dadosAtualizados) {
        UUID uuid = UUID.fromString(id);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(uuid);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            if (dadosAtualizados.getNome() != null) {
                usuarioExistente.setNome(dadosAtualizados.getNome());
            }
            if (dadosAtualizados.getEmail() != null) {
                usuarioExistente.setEmail(dadosAtualizados.getEmail());
            }
            usuarioRepository.save(usuarioExistente);
        }
    }

    public void deletarPorId(String id) {
        UUID uuid = UUID.fromString(id);
        if (usuarioRepository.existsById(uuid)) {
            usuarioRepository.deleteById(uuid);
        }
    }
}
