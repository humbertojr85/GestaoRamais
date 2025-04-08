package com.midiavox.gestaoramais.controller;

import com.midiavox.gestaoramais.model.Ramal;
import com.midiavox.gestaoramais.repository.RamalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ramais")
public class RamalController {

    private final RamalService ramalService;

    public RamalController(RamalService ramalService) {
        this.ramalService = ramalService;
    }

    @GetMapping
    public ResponseEntity<List<Ramal>> listarRamais() {
        return ResponseEntity.ok(ramalService.listarTodos());
    }

    @PostMapping("/criar")
    public ResponseEntity<Ramal> criarRamal(@RequestBody Map<String, String> body) {
        String numero = body.get("numero");
        Ramal novo = ramalService.criarRamal(numero);
        return ResponseEntity.status(201).body(novo);
    }

    @PostMapping("/{ramalId}/logar")
    public ResponseEntity<Void> logar(@PathVariable UUID ramalId, @RequestBody Map<String, String> body) {
        UUID usuarioId = UUID.fromString(body.get("usuarioId"));
        ramalService.logarUsuarioEmRamal(usuarioId, ramalId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logoff/{usuarioId}")
    public ResponseEntity<Void> deslogar(@PathVariable UUID usuarioId) {
        ramalService.deslogarUsuario(usuarioId);
        return ResponseEntity.ok().build();
    }
}