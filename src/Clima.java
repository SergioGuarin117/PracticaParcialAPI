import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Clima {
    void main(String[] args) throws Exception {
        double latitud = 4.0847;
        double longitud = -76.1954;

        String url = "https://api.open-meteo.com/v1/forecast" + "?latitude=" + latitud + "&longitude=" + longitud
                + "&current=temperature_2m,wind_speed_10m";


        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> respuesta = cliente.send(solicitud,HttpResponse.BodyHandlers.ofString());

        System.out.println("Estado HTTP: "+ respuesta.statusCode());
        System.out.println("Respuesta de la API: ");
        System.out.println(respuesta.body());

        if (respuesta.statusCode()==200) {
            JsonObject json = JsonParser.parseString(respuesta.body()).getAsJsonObject();
            JsonObject current = json.getAsJsonObject("current");
            JsonObject medida = json.getAsJsonObject("current_units");

            double temperatura = current.get("temperature_2m").getAsDouble();
            System.out.println("Temperatura: " + temperatura + " °C");

            double viento = current.get("wind_speed_10m").getAsDouble();
            String kilo = medida.get("wind_speed_10m").getAsString();
            System.out.println("La velocidad del viento es: " + viento + " " + kilo);
        }


    }
}
