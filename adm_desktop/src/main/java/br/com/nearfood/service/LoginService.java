
package br.com.nearfood.service;

/**
 *
 * @author firmodev
 */


import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public class LoginService {
    private static final String API_URL = "http://localhost:8000/api/usuarios/login/";

    public static boolean autenticar(String username, String password) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }

            int status = conn.getResponseCode();
            InputStreamReader reader = status >= 200 && status < 300
                    ? new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
                    : new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(reader)) {
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
            }

            System.out.println("📩 Resposta da API: " + response);
            if (status == 200) {
                return true;
            } else {
                return false; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
