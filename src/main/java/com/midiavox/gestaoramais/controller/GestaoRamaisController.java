package com.midiavox.gestaoramais.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gestao-ramais")
public class GestaoRamaisController {
    //Post, Get, Put, Delete, etc.
    @GetMapping()
    public String ramais() {
        return "Lista de ramais";
    }
}
