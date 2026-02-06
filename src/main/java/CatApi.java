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

    public static void main(String[] args){
        getRandomCats(3);
    }

    public static void getRandomCats(int limit){
        String url = String.format(Locale.US,"%s?limit=%d",API_BASE_URL, limit);
        System.out.println(url);

        //Create an HTTP client
        HttpClient httpClient = HttpClient.newHttpClient();

        //Create request
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        //Send request and get answer


    }

}
