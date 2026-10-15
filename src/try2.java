import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class try2 {
    static void main() throws IOException, InterruptedException {
        double latitud = 4.6097;
        double longitud = -74.0817;

        String url = "https://api.open-meteo.com/v1/forecast" +
                "?latitude=" +latitud + "&longitude=" + longitud +
                "&hourly=apparent_temperature";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Estado de la API: " + response.statusCode());
        System.out.println(response.body());

        if (response.statusCode()==200){
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            System.out.println("Este es el horario de Bogota");

            if (json.has("hourly")){
                JsonObject hourly = json.getAsJsonObject("hourly");
                JsonArray time = hourly.getAsJsonArray("time");
                JsonArray sensaciones = hourly.getAsJsonArray("apparent_temperature");

                for (int i = 0; i < time.size();i++){
                    String hora = time.get(i).getAsString();
                    double sensacion = sensaciones.get(i).getAsDouble();

                    System.out.println(hora + " - " + sensacion + " °C");
                }
                }else {
                System.out.println("La respuesta no contiene datos horarios");
                System.out.println(response.body());
            }
        }
    }
}
