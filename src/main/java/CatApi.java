import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

public class CatApi {
    //API KEY: null

    private static final String API_BASE_URL = "https://api.thecatapi.com/v1/images/search";

    public static void main(String[] args) throws IOException, InterruptedException {
        getRandomCats(3);
    }

    public static void getRandomCats(int limit) throws IOException, InterruptedException {
        String url = String.format(Locale.US,"%s?limit=%d",API_BASE_URL, limit);
        System.out.println(url);

        // Create an HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send request and get answer
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Check if request was successful
        if(response.statusCode()==200){
            //Parse the array
            JsonArray jsonResponse = JsonParser.parseString(response.body()).getAsJsonArray();

            //Loop through the array
            for(int i =0; i< jsonResponse.size(); i++){
                //Get one cat from the array
                JsonObject catImage = jsonResponse.get(i).getAsJsonObject();

                //Extract the fields of the cat i
                String id = catImage.get("id").getAsString();
                String urlResponse = catImage.get("url").getAsString();
                int width = catImage.get("width").getAsInt();
                int height = catImage.get("height").getAsInt();

                //Display the result
                System.out.println("========= CAT " + (i+1) + " =========");
                System.out.println("ID: " + id);
                System.out.println("URL: " + urlResponse);
                System.out.println("WIDTH: " + width);
                System.out.println("HEIGHT: " + height);
                System.out.println();
            }

        }


    }

}
