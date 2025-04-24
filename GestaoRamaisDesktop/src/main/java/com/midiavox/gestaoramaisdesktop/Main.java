package com.midiavox.gestaoramaisdesktop;

import com.midiavox.gestaoramaisdesktop.service.RamalService;
import com.midiavox.gestaoramaisdesktop.service.UsuarioService;
import com.midiavox.gestaoramaisdesktop.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        RamalService ramalService = RetrofitConfig.getRetrofit().create(RamalService.class);
        UsuarioService usuarioService = RetrofitConfig.getRetrofit().create(UsuarioService.class);

        MainView mainView = new MainView(ramalService, usuarioService);
        mainView.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
