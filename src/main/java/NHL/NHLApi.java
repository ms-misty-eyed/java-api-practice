package NHL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class NHLApi {
    //Used https://github.com/Zmalski/NHL-API-Reference.git

    private static final String BASE_ROSTER_URL = "https://api-web.nhle.com/v1/roster/";
    static Scanner sc = new Scanner(System.in);

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
        //Ask tne question infinitely
        for(int i =0; ; i++){
            //1. get a player, asks for a team
            //askQuestion();
            //2. give a team and a jersey member, ask for last name
            askLastName();
        }
    }

    private static Team getTeam(){
        //Generate a random team
        int randomTeamCode = (int) ((Math.random() * 32));
        return teams.get(randomTeamCode);
    }

    private static Player getPlayer() throws IOException, InterruptedException {
        Team team = getTeam();
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
            int jerseyNumber = player.has("sweaterNumber") ? player.get("sweaterNumber").getAsInt() : 0;

            return new Player(name, lastName, jerseyNumber, team.code, team.city, team.name);

            //If response was unsuccessful
        } else {
            System.out.println("Bad status code: " + response.statusCode());
        }
        return null;
    }

    public static void askQuestion() throws IOException, InterruptedException {

        //Get a player
        Player player = getPlayer();
        String name = player.getName();
        String lastName = player.getLastName();
        int jerseyNumber = player.getJerseyNumber();


            //Ask the question
            System.out.println();
            String question = String.format("For what team does %s %s (Jersey number %d) plays for?", name, lastName, jerseyNumber);
            System.out.println(question);

            //Create a Scanner and read input
            String input = sc.nextLine();

            //Compare the results
            if (input.equalsIgnoreCase(player.teamName) ||
                input.equalsIgnoreCase(player.teamCity) ||
                input.equalsIgnoreCase(player.teamCode)) {
                System.out.println("Good answer!!");
            } else {

                //String.format("Wrong answer: He plays for the %s %s", player.teamCity, player.teamName);
                System.out.println(String.format("Wrong answer: He plays for the %s %s", player.teamCity, player.teamName));
        }
    }

    public static void askLastName() throws IOException, InterruptedException {
        //Get a player
        Player player = getPlayer();
        String name = player.getName();
        String lastName = player.getLastName();
        int jerseyNumber = player.getJerseyNumber();

        String teamName = player.getTeamName();
        String teamCity = player.getTeamCity();

        //Ask the question
        String question = String.format("What's the last name of the player wearing number %d on the %s %s",
                jerseyNumber, teamCity, teamName);
        System.out.println();
        System.out.println(question);
        String answer = sc.nextLine();

        //Verify the answer
        if(answer.equalsIgnoreCase(lastName)){
            System.out.println("Good Answer!");
        }
        else{
            String goodAnswer = String.format("%s %s wears number %d for the %s %s",
                    name, lastName, jerseyNumber, teamCity, teamName);
            System.out.println(goodAnswer);
        }
    }
}
