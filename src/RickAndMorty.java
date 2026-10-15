import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RickAndMorty {
    static void main() throws IOException, InterruptedException {

        String url = "https://rickandmortyapi.com/api/character/?name=rick&status=alive";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse<String> respuesta = client.send(solicitud, HttpResponse.BodyHandlers.ofString());

        System.out.println(respuesta.statusCode());
        System.out.println(respuesta.body());

        if (respuesta.statusCode()==200) {
            JsonObject json = JsonParser.parseString(respuesta.body()).getAsJsonObject();
            JsonArray personajes = json.getAsJsonArray("results");

            for (int i= 0; i < personajes.size(); i++){
                JsonObject personaje = personajes.get(i).getAsJsonObject();
                String id = personaje.get("id").getAsString();
                String nombre = personaje.get("name").getAsString();
                String especie = personaje.get("species").getAsString();
                String locacion = personaje.getAsJsonObject("location").get("name").getAsString();

                System.out.println("ID: "+ id + "\nNombre: "+ nombre + "\nEspecie: "+ especie +"\n"+ "Locacion: "+locacion + "\n--------\n");

                System.out.println("Made in UV");

            }
        }

    }
}
