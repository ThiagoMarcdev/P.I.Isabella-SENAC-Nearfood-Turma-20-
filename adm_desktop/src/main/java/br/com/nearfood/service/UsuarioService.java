package br.com.nearfood.service;

import br.com.nearfood.models.Usuario;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class UsuarioService {

    private final String BASE_URL = "http://localhost:8000/usuarios/api/usuarios"; // Endereço da API
    private final HttpClient client;
    private final Gson gson;

    public UsuarioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // Busca dados do usuário (GET)
    public CompletableFuture<Usuario> buscarUsuario(Long id) {
        String url = BASE_URL + "/" + id + "/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)) 
                .GET()
                .header("Accept", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(json -> gson.fromJson(json, Usuario.class));
    }

    // Atualiza dados (PUT)
    public CompletableFuture<Boolean> atualizarUsuario(Usuario usuario) {
        String jsonBody = gson.toJson(usuario);

        // Montagem: URL + / + ID + /
        // ISSO GARANTE QUE O DJANGO NÃO DÊ ERRO 301 OU 404
        String url = BASE_URL + "/" + usuario.getId() + "/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    // Log para você conferir se deu certo
                    System.out.println("PUT Status: " + response.statusCode());
                    return response.statusCode() == 200 || response.statusCode() == 204;
                });
    }
}
