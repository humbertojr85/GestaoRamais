package com.midiavox.gestaoramais.controller;

import com.midiavox.gestaoramais.repository.UsuarioRepository;
import com.midiavox.gestaoramais.repository.RamalRepository;
import com.midiavox.gestaoramais.model.Ramal;
import org.springframework.beans.factory.annotation.Autowired;
import com.midiavox.gestaoramais.model.Usuario;
import com.midiavox.gestaoramais.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RamalRepository ramalRepository;

    @GetMapping
    public List<Map<String, Object>> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> {
            Map<String, Object> usuarioMap = new HashMap<>();
            usuarioMap.put("id", usuario.getId());
            usuarioMap.put("nome", usuario.getNome());
            usuarioMap.put("email", usuario.getEmail());

            ramalRepository.findByUsuarioLogado(usuario).ifPresent(r -> {
                Map<String, Object> ramalMap = new HashMap<>();
                ramalMap.put("id", r.getId());
                ramalMap.put("numero", r.getNumero());
                usuarioMap.put("ramal", ramalMap);
            });

            return usuarioMap;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}