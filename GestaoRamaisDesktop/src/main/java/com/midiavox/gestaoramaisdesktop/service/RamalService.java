package com.midiavox.gestaoramaisdesktop.service;

import com.midiavox.gestaoramaisdesktop.model.Ramal;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface RamalService {

    // Listar ramais disponíveis
    @GET("/ramais/disponiveis")
    Call<List<Ramal>> listarDisponiveis();

    // Gerar faixa de ramais
    @POST("/ramais/gerar")
    Call<Void> gerarRamais(@Body Map<String, Integer> faixa);

    // Excluir faixa de ramais
    @DELETE("/ramais/excluir-faixa")
    Call<Void> excluirFaixa(@Body Map<String, Integer> faixa);

    // Logar usuário em ramal
    @POST("/ramais/logar")
    Call<Void> logarRamal(@Body Map<String, String> dados);

    // Logoff de ramal
    @POST("/ramais/logoff")
    Call<Void> logoff(@Body Map<String, String> dados);
    
}
