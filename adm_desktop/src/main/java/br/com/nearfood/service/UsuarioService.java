package br.com.nearfood.service;

import br.com.nearfood.models.Usuario;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public class UsuarioService {

     private static final String API_URL = "http://localhost:8000/api/usuarios/";

    public static boolean cadastrarUsuario(Usuario usuario) {
        try {
            // Monta a conexão HTTP
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Converte o objeto Usuario para JSON automaticamente
            Gson gson = new Gson();
            String jsonInput = gson.toJson(usuario);

            // Envia o JSON
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();

            // Lê resposta da API (inputStream ou errorStream)
            InputStreamReader reader;
            if (status >= 200 && status < 300) {
                reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            } else {
                reader = new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);
            }

            try (BufferedReader br = new BufferedReader(reader)) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                System.out.println("📩 Resposta da API: " + response.toString());
            }

            conn.disconnect();

            if (status == 201 || status == 200) {
                JOptionPane.showMessageDialog(null, "✅ Usuário cadastrado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "❌ Erro ao cadastrar. Código HTTP: " + status);
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
