package com.midiavox.gestaoramaisdesktop.view;

import com.midiavox.gestaoramaisdesktop.model.Ramal;
import com.midiavox.gestaoramaisdesktop.model.Usuario;
import com.midiavox.gestaoramaisdesktop.service.RamalService;
import com.midiavox.gestaoramaisdesktop.service.UsuarioService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MainView {

    private final RamalService ramalService;
    private final UsuarioService usuarioService;

    private final ObservableList<Usuario> usuariosNaoLogados = FXCollections.observableArrayList();
    private final ObservableList<Ramal> ramaisDisponiveis = FXCollections.observableArrayList();
    private final ObservableList<Usuario> usuariosLogados = FXCollections.observableArrayList();

    public MainView(RamalService ramalService, UsuarioService usuarioService) {
        this.ramalService = ramalService;
        this.usuarioService = usuarioService;
    }

    public void mostrar(Stage stage) {
        // Campos de entrada
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField inicioField = new TextField();
        inicioField.setPromptText("Ramal Inicial");

        TextField fimField = new TextField();
        fimField.setPromptText("Ramal Final");

        ComboBox<Usuario> usuarioCombo = new ComboBox<>(usuariosNaoLogados);
        ComboBox<Ramal> ramalCombo = new ComboBox<>(ramaisDisponiveis);

        ListView<Usuario> listaUsuariosNaoLogados = new ListView<>(usuariosNaoLogados);
        ListView<Ramal> listaRamaisDisponiveis = new ListView<>(ramaisDisponiveis);

        // Botões
        Button cadastrarBtn = new Button("Cadastrar");
        Button gerarBtn = new Button("Gerar Ramais");
        Button excluirBtn = new Button("Excluir Ramais");
        Button logarBtn = new Button("Logar");

        // Tabela de usuários logados
        TableView<Usuario> tabelaLogados = new TableView<>(usuariosLogados);
        TableColumn<Usuario, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Usuario, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Usuario, String> ramalCol = new TableColumn<>("Ramal");
        ramalCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getRamal() != null ? data.getValue().getRamal().getNumero() : ""));

        TableColumn<Usuario, Void> acaoCol = new TableColumn<>("Ação");
        acaoCol.setCellFactory(col -> new TableCell<>() {
            private final Button logoffBtn = new Button("Logoff");
            {
                logoffBtn.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
                logoffBtn.setOnAction(e -> {
                    Usuario u = getTableView().getItems().get(getIndex());
                    Map<String, String> payload = new HashMap<>();
                    payload.put("idRamal", u.getRamal().getId());
                    ramalService.logoff(payload).enqueue(new Callback<>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                mostrarAlerta("Sucesso", "Logoff realizado!");
                                carregarDados();
                            } else {
                                mostrarErro("Erro", "Erro ao fazer logoff: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            mostrarErro("Erro", "Erro ao fazer logoff: " + t.getMessage());
                        }
                    });
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : logoffBtn);
            }
        });

        tabelaLogados.getColumns().addAll(nomeCol, emailCol, ramalCol, acaoCol);

        // Layouts
        VBox cadastrarBox = new VBox(5, new Label("1. Cadastrar Usuário"), nomeField, emailField, cadastrarBtn);
        VBox gerarBox = new VBox(5, new Label("2. Gerar Ramais"), inicioField, fimField, new HBox(5, gerarBtn, excluirBtn));
        VBox logarBox = new VBox(5, new Label("3. Logar em Ramal"), usuarioCombo, ramalCombo, logarBtn);
        HBox linha1 = new HBox(20, cadastrarBox, gerarBox, logarBox);

        VBox naoLogadosBox = new VBox(5, new Label("4. Usuários Não Logados"), listaUsuariosNaoLogados);
        VBox ramaisBox = new VBox(5, new Label("5. Ramais Disponíveis"), listaRamaisDisponiveis);
        HBox linha2 = new HBox(20, naoLogadosBox, ramaisBox);

        VBox linha3 = new VBox(5, new Label("6. Usuários Logados"), tabelaLogados);

        VBox root = new VBox(15, linha1, linha2, linha3);
        root.setPadding(new Insets(15));

        // Ações
        cadastrarBtn.setOnAction(e -> {
            Usuario u = new Usuario(nomeField.getText(), emailField.getText());
            usuarioService.cadastrar(u).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        mostrarAlerta("Sucesso", "Usuário cadastrado!");
                        carregarDados();
                        nomeField.clear();
                        emailField.clear();
                    } else {
                        mostrarErro("Erro", "Erro ao cadastrar: " + response.code());
                        System.err.println("Erro ao cadastrar: Código " + response.code() + ", Mensagem: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    mostrarErro("Erro", "Erro ao cadastrar: " + t.getMessage());
                    System.err.println("Erro ao cadastrar: " + t.getMessage());
                }
            });
        });

        gerarBtn.setOnAction(e -> {
            try {
                Map<String, Integer> faixa = new HashMap<>();
                faixa.put("inicio", Integer.parseInt(inicioField.getText()));
                faixa.put("fim", Integer.parseInt(fimField.getText()));
                ramalService.gerarRamais(faixa).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            mostrarAlerta("Sucesso", "Ramais gerados com sucesso!");
                            carregarDados();
                            inicioField.clear();
                            fimField.clear();
                        } else {
                            mostrarErro("Erro", "Erro ao gerar ramais: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mostrarErro("Erro", "Erro ao gerar ramais: " + t.getMessage());
                    }
                });
            } catch (NumberFormatException ex) {
                mostrarErro("Erro", "Por favor, insira números válidos para o intervalo de ramais.");
            }
        });

        excluirBtn.setOnAction(e -> {
            try {
                Map<String, Integer> faixa = new HashMap<>();
                faixa.put("inicio", Integer.parseInt(inicioField.getText()));
                faixa.put("fim", Integer.parseInt(fimField.getText()));
                ramalService.excluirFaixa(faixa).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            mostrarAlerta("Sucesso", "Ramais excluídos com sucesso!");
                            carregarDados();
                            inicioField.clear();
                            fimField.clear();
                        } else {
                            mostrarErro("Erro", "Erro ao excluir ramais: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mostrarErro("Erro", "Erro ao excluir ramais: " + t.getMessage());
                    }
                });
            } catch (NumberFormatException ex) {
                mostrarErro("Erro", "Por favor, insira números válidos para o intervalo de ramais.");
            }
        });

        logarBtn.setOnAction(e -> {
            Usuario u = usuarioCombo.getValue();
            Ramal r = ramalCombo.getValue();
            if (u != null && r != null) {
                Map<String, String> dados = new HashMap<>();
                dados.put("idUsuario", u.getId());
                dados.put("idRamal", r.getId());
                ramalService.logarRamal(dados).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            mostrarAlerta("Sucesso", "Usuário logado com sucesso!");
                            carregarDados();
                        } else {
                            mostrarErro("Erro", "Erro ao logar: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mostrarErro("Erro", "Erro ao logar: " + t.getMessage());
                    }
                });
            } else {
                mostrarErro("Erro", "Por favor, selecione um usuário e um ramal.");
            }
        });

        // Scene
        stage.setScene(new Scene(root, 1100, 750));
        stage.setTitle("Gestão de Ramais - Desktop");
        stage.show();

        carregarDados();
    }

    private void carregarDados() {
        usuarioService.listarUsuarios().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    usuariosNaoLogados.clear();
                    usuariosLogados.clear();
                    if (response.body() != null) {
                        for (Usuario u : response.body()) {
                            if (u.getRamal() == null) {
                                usuariosNaoLogados.add(u);
                            } else {
                                usuariosLogados.add(u);
                            }
                        }
                    }
                } else {
                    mostrarErro("Erro", "Erro ao carregar usuários: " + response.code());
                    System.err.println("Erro ao carregar usuários: Código " + response.code() + ", Mensagem: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                mostrarErro("Erro", "Erro ao carregar usuários: " + t.getMessage());
                System.err.println("Erro ao carregar usuários: " + t.getMessage());
            }
        });

        ramalService.listarDisponiveis().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Ramal>> call, Response<List<Ramal>> response) {
                if (response.isSuccessful()) {
                    ramaisDisponiveis.setAll(response.body());
                } else {
                    mostrarErro("Erro", "Erro ao carregar ramais: " + response.code());
                    System.err.println("Erro ao carregar ramais: Código " + response.code() + ", Mensagem: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Ramal>> call, Throwable t) {
                mostrarErro("Erro", "Erro ao carregar ramais: " + t.getMessage());
                System.err.println("Erro ao carregar ramais: " + t.getMessage());
            }
        });
    }

    private void mostrarAlerta(String titulo, String msg) {
        Platform.runLater(() -> {
            System.out.println("ALERTA (FX Thread): Título='" + titulo + "', Mensagem='" + msg + "'");
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setTitle(titulo);
            a.setContentText(msg);
            a.showAndWait();
        });
    }
    
    private void mostrarErro(String titulo, String msg) {
        Platform.runLater(() -> {
            System.err.println("ERRO (FX Thread): Título='" + titulo + "', Mensagem='" + msg + "'");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(null);
            a.setTitle(titulo);
            a.setContentText(msg);
            a.showAndWait();
        });
    }
}