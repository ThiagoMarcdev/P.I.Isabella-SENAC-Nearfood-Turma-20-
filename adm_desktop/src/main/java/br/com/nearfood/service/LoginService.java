package br.com.nearfood.service;

import br.com.nearfood.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class LoginService {

    private static final String API_URL = "http://localhost:8000/usuarios/api/login/";
    private final HttpClient client;
    private final Gson gson;

    public LoginService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public CompletableFuture<Usuario> fazerLogin(String username, String password) {
        // 1. Monta o JSON
        JsonObject json = new JsonObject();
        json.addProperty("username", username); // O Django geralmente espera "username", não "user"
        json.addProperty("password", password);

        String requestBody = gson.toJson(json);

        // 2. Cria a Requisição POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // 3. Envia Assincronamente
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    
                    // --- ADICIONE ESTAS LINHAS PARA DEBUG ---
                System.out.println("STATUS: " + response.statusCode());
                System.out.println("JSON RECEBIDO: " + response.body()); 
                // ----------------------------------------
                    if (response.statusCode() == 200) {
                        // Se deu certo (200 OK), o Django retorna o JSON do usuário
                        // Convertemos esse JSON para o objeto Java Usuario
                        return gson.fromJson(response.body(), Usuario.class);
                    } else {
                        // Se deu erro (401, 403, etc), retornamos null ou lançamos erro
                        System.out.println("Erro Login: " + response.statusCode() + " - " + response.body());
                        return null;
                    }
                });
    }
}