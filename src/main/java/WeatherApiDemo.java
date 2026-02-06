import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Simple Weather API Demo using Open-Meteo (free, no API key needed!)
 * This demonstrates how to make HTTP requests to a REST API in Java
 */
public class WeatherApiDemo {

    // Open-Meteo API - completely free, no API key required
    private static final String API_BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public static void main(String[] args) {
        try {
            // Example 1: Get weather for San Francisco
            System.out.println("=== Weather in San Francisco ===");
            getWeather(37.7749, -122.4194, "San Francisco");
            System.out.println();

            // Example 2: Get weather for New York
            System.out.println("=== Weather in New York ===");
            getWeather(40.7128, -74.0060, "New York");
            System.out.println();

            // Example 3: Get weather for Tokyo
            System.out.println("=== Weather in Tokyo ===");
            getWeather(35.6762, 139.6503, "Tokyo");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets current weather for a location using latitude and longitude
     *
     * @param latitude The latitude of the location
     * @param longitude The longitude of the location
     * @param cityName Name of the city (for display purposes)
     */
    public static void getWeather(double latitude, double longitude, String cityName)
            throws IOException, InterruptedException {

        // Build the URL with query parameters
        String url = String.format(Locale.US,
                "%s?latitude=%.4f&longitude=%.4f&current_weather=true&temperature_unit=celsius",
                API_BASE_URL, latitude, longitude
        );

        // Create HTTP client
        //System.out.println("Requesting URL: " + url);
        HttpClient client = HttpClient.newHttpClient();

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if request was successful
        if (response.statusCode() == 200) {
            // Parse JSON response
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject currentWeather = jsonResponse.getAsJsonObject("current_weather");

            // Extract weather data
            double temperature = currentWeather.get("temperature").getAsDouble();
            double windSpeed = currentWeather.get("windspeed").getAsDouble();
            int weatherCode = currentWeather.get("weathercode").getAsInt();

            // Display the results
            System.out.println("City: " + cityName);
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Wind Speed: " + windSpeed + " mph");
            System.out.println("Condition: " + getWeatherDescription(weatherCode));

        } else {
            System.err.println("API request failed with status code: " + response.statusCode());
        }
    }

    /**
     * Converts weather code to human-readable description
     * Weather codes from WMO (World Meteorological Organization)
     */
    private static String getWeatherDescription(int code) {
        switch (code) {
            case 0: return "Clear sky";
            case 1: case 2: case 3: return "Partly cloudy";
            case 45: case 48: return "Foggy";
            case 51: case 53: case 55: return "Drizzle";
            case 61: case 63: case 65: return "Rain";
            case 71: case 73: case 75: return "Snow";
            case 77: return "Snow grains";
            case 80: case 81: case 82: return "Rain showers";
            case 85: case 86: return "Snow showers";
            case 95: return "Thunderstorm";
            case 96: case 99: return "Thunderstorm with hail";
            default: return "Unknown";
        }
    }
}