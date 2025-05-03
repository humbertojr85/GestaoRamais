package com.midiavox.gestaoramaisdesktop.service;

import com.midiavox.gestaoramaisdesktop.model.Usuario;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsuarioService {

    // Listar todos os usuários
    @GET("/usuarios")
    Call<List<Usuario>> listarUsuarios();

    // Cadastrar novo usuário
    @POST("/usuarios")
    Call<Usuario> cadastrar(@Body Usuario usuario);
}
