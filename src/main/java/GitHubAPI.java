import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GitHubAPI {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter the name of a github user to look up:");
            getGitHubUser(sc.nextLine());
        }
    }

    public static void getGitHubUser(String username) throws IOException, InterruptedException {

        // Build URL
        String url = String.format("https://api.github.com/users/%s", username);

        // Make request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        // Parse JSON
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 200){
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject gitHubProfile = jsonResponse.getAsJsonObject();

            // Extract fields and display
            String login = gitHubProfile.get("login").getAsString();
            String name = gitHubProfile.get("name").getAsString();
            String location = gitHubProfile.get("location").getAsString();
            String avatarUrl = gitHubProfile.get("avatar_url").getAsString();
            String userViewType = gitHubProfile.get("user_view_type").getAsString();
            int id = gitHubProfile.get("id").getAsInt();
            int followers = gitHubProfile.get("followers").getAsInt();
            int following = gitHubProfile.get("following").getAsInt();

            //Print!
            System.out.println("=========== GitHub Profile ===========");
            System.out.println("Username: " + login + " (id: " + id +")");
            System.out.println("Name: " + name);
            System.out.println("Located in " + location);
            System.out.println("Has a " + userViewType +" account");
            System.out.println("Has " + followers +(followers > 1?" followers": " follower"));
            System.out.println("Is following " + following + (following > 1?" accounts": " account"));
            System.out.println("Profile picture: " + avatarUrl);
            System.out.println();
        }
    }
}
