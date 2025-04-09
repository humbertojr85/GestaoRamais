package com.midiavox.gestaoramais.controller;

import com.midiavox.gestaoramais.model.Ramal;
import com.midiavox.gestaoramais.model.Usuario;
import com.midiavox.gestaoramais.service.RamalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ramais")
@CrossOrigin(origins = "*")
public class RamalController {

    private final RamalService ramalService;

    public RamalController(RamalService ramalService) {
        this.ramalService = ramalService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<String> gerarRamais(@RequestBody Map<String, Integer> faixa) {
        Integer inicio = faixa.get("inicio");
        Integer fim = faixa.get("fim");
        ramalService.cadastrarFaixaRamal(inicio, fim);
        return ResponseEntity.ok("Ramais gerados com sucesso");
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Ramal>> listarDisponiveis() {
        return ResponseEntity.ok(ramalService.listarDisponiveis());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRamal(@PathVariable UUID id) {
        ramalService.deletarRamal(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logar")
    public ResponseEntity<String> logarRamal(@RequestBody Map<String, String> dados) {
        UUID idUsuario = UUID.fromString(dados.get("idUsuario"));
        String idRamal = dados.get("idRamal");

        Ramal ramal = ramalService.getRamalById(UUID.fromString(idRamal));
        ramalService.logarRamal(idUsuario, ramal.getNumero());

        return ResponseEntity.ok("Usuário logado no ramal");
    }

    @PostMapping("/logoff")
    public ResponseEntity<String> logoff(@RequestBody Map<String, String> dados) {
        UUID idRamal = UUID.fromString(dados.get("idRamal"));
        Ramal ramal = ramalService.getRamalById(idRamal);
        if (ramal.getUsuarioLogado() != null) {
            ramalService.logoff(ramal.getUsuarioLogado().getId());
        }
        return ResponseEntity.ok("Logoff realizado com sucesso");
    }
}
