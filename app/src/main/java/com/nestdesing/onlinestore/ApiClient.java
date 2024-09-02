package com.nestdesign.onlinestore.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    private static final String TAG = "ApiClient";

    // Constantes de configuración
    private static final String ID_SCPT = "AKfycbxygSIACVCEstKaIzYPw7HTXSL88k8p2txNwlAR93ogCFWZ2hePdSXrNRXqxL0pb4e7";
    private static final String API_KEY = "apiDemoSENA";
    private static final String BASE_URL = "https://script.google.com/macros/s/" + ID_SCPT + "/exec?api_key=" + API_KEY;

    // Método para hacer una petición GET
    public static String get(String queryParams) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BASE_URL + "&" + queryParams);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Leer la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Retornar la respuesta en formato de String
            return response.toString();

        } catch (Exception e) {
            Log.e(TAG, "Error en la conexión HTTP: " + e.getMessage(), e);
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    // Método para hacer una petición POST
    public static String post(String endpointUrl, String postData) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BASE_URL + "&" + endpointUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);

            // Enviar los datos de la petición POST
            try (OutputStream os = urlConnection.getOutputStream()) {
                os.write(postData.getBytes("UTF-8"));
                os.flush();
            }

            // Leer la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Retornar la respuesta en formato de String
            return response.toString();

        } catch (Exception e) {
            Log.e(TAG, "Error en la conexión HTTP: " + e.getMessage(), e);
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
