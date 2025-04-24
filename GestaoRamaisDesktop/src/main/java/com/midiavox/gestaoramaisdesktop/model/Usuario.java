package com.midiavox.gestaoramaisdesktop.model;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private Ramal ramal;

    public Usuario() {}

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Ramal getRamal() { return ramal; }

    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setRamal(Ramal ramal) { this.ramal = ramal;}

    @Override
    public String toString() {
        return nome + " (" + email +")"; // qualquer atributo que queira exibir
    }
}
