package server;

import exceptions.responseCodeException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String URL;
    private String API_TOKEN = "";

    public KVTaskClient(String URL) {
        this.URL = URL;
        register();
    }

    private void register() {
        URI uri = URI.create(URL + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
            if (response.statusCode() != 200) {
                throw new responseCodeException();
            }
        } catch (IOException | InterruptedException | responseCodeException e) {
            e.printStackTrace();
        }

        if (response.body() != null) {
            API_TOKEN = response.body();
            System.out.println("client api " + API_TOKEN);
        }
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI uri = URI.create(URL + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        System.out.println(URL + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<Void> handler = HttpResponse.BodyHandlers.discarding();
        HttpResponse<Void> response;
        try {
            response = client.send(request, handler);
            if (response.statusCode() != 200) {
                throw new responseCodeException();
            }
        } catch (responseCodeException e) {
            e.printStackTrace();
        }
    }

    public String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(URL + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
            if (response.statusCode() != 200) {
                throw new responseCodeException();
            }
        } catch (responseCodeException e) {
            e.printStackTrace();
        }

        return response.body();
    }
}
