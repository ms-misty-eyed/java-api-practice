import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NHLApi {
    //Used https://github.com/Zmalski/NHL-API-Reference.git

    private static final String BASE_ROSTER_URL = "https://api-web.nhle.com/v1/roster/";

    record Team(String code, String city, String name) {}
    private static final List<Team> teams = List.of(
            new Team("ANA", "Anaheim", "Ducks"),
            new Team("BOS", "Boston", "Bruins"),
            new Team("BUF", "Buffalo", "Sabres"),
            new Team("CAR", "Carolina", "Hurricanes"),
            new Team("CBJ", "Columbus", "Blue Jackets"),
            new Team("CGY", "Calgary", "Flames"),
            new Team("CHI", "Chicago", "Blackhawks"),
            new Team("COL", "Colorado", "Avalanche"),
            new Team("DAL", "Dallas", "Stars"),
            new Team("DET", "Detroit", "Red Wings"),
            new Team("EDM", "Edmonton", "Oilers"),
            new Team("FLA", "Florida", "Panthers"),
            new Team("LAK", "Los Angeles", "Kings"),
            new Team("MIN", "Minnesota", "Wild"),
            new Team("MTL", "Montreal", "Canadiens"),
            new Team("NJD", "New Jersey", "Devils"),
            new Team("NSH", "Nashville", "Predators"),
            new Team("NYI", "New York", "Islanders"),
            new Team("NYR", "New York", "Rangers"),
            new Team("OTT", "Ottawa", "Senators"),
            new Team("PHI", "Philadelphia", "Flyers"),
            new Team("PIT", "Pittsburgh", "Penguins"),
            new Team("SEA", "Seattle", "Kraken"),
            new Team("SJS", "San Jose", "Sharks"),
            new Team("STL", "St. Louis", "Blues"),
            new Team("TBL", "Tampa Bay", "Lightning"),
            new Team("TOR", "Toronto", "Maple Leafs"),
            new Team("UTA", "Utah", "Hockey Club"),
            new Team("VAN", "Vancouver", "Canucks"),
            new Team("VGK", "Vegas", "Golden Knights"),
            new Team("WPG", "Winnipeg", "Jets"),
            new Team("WSH", "Washington", "Capitals")
    );

    public static void main (String[] args) throws IOException, InterruptedException {
        getPlayer();
    }

    public static void getPlayer() throws IOException, InterruptedException {
        // Get a team, get a position, get a player

        //Random mode

        //Get a team code with index 0-32
        int randomTeamCode = (int) ((Math.random() * 32));
        Team team = teams.get(randomTeamCode);

        String url = String.format(BASE_ROSTER_URL + "%s/current", team.code());

        //Create an HTTP Client
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        //Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        //Send request, get answer
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Check if response was successful
        if (response.statusCode() == 200) {
            //Parse array and loop through
            JsonObject rosterData = JsonParser.parseString(response.body()).getAsJsonObject();

            //Get a random index from the array
            JsonArray forwards = rosterData.getAsJsonArray("forwards");
            JsonArray defensemen = rosterData.getAsJsonArray("defensemen");
            JsonArray goalies = rosterData.getAsJsonArray("goalies");

            JsonArray allPlayers = new JsonArray();
            allPlayers.addAll(forwards);
            allPlayers.addAll(defensemen);
            allPlayers.addAll(goalies);

            int randomIndex = (int) (Math.random() * allPlayers.size());
            JsonObject player = allPlayers.get(randomIndex).getAsJsonObject();

            //Extract his name, jersey number and position
            String name = player.get("firstName").getAsJsonObject().get("default").getAsString();
            String lastName = player.get("lastName").getAsJsonObject().get("default").getAsString();
            int jerseyNumber = player.get("sweaterNumber").getAsInt();

            //Test result
            System.out.println(name + " " + lastName + " " + jerseyNumber);
        }else {
            System.out.println("Bad status code: " + response.statusCode());  // ADD THIS
        }
    }
}
