import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * CityWeatherApp - A Java application that fetches weather data for any city using REST APIs.
 * Uses Open-Meteo's Geocoding API (for city coordinates) and Weather API (for weather data).
 */
public class CityWeatherApp {
    // API endpoints
    private static final String GEOCODING_API = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WEATHER_API = "https://api.open-meteo.com/v1/forecast";
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main entry point of the application.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            System.out.println("India Weather App");
            System.out.println("----------------");
            
            // Step 1: Get city name input from user
            System.out.print("Enter city name: ");
            String cityName = scanner.nextLine().trim();
            
            // Step 2: Fetch coordinates for the city
            JSONObject locationData = getCityCoordinates(cityName);
            if (locationData == null) {
                System.out.println("City not found. Please try again.");
                return;
            }
            
            // Extract location details from API response
            double latitude = locationData.getDouble("latitude");
            double longitude = locationData.getDouble("longitude");
            String city = locationData.getString("name");
            String country = locationData.getString("country");
            
            // Step 3: Get weather data using coordinates
            JSONObject weatherData = getWeatherData(latitude, longitude);
            if (weatherData != null) {
                // Step 4: Display formatted weather information
                displayWeather(weatherData, city, country);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close(); // Ensure scanner is closed
        }
    }

    /**
     * Fetches latitude/longitude coordinates for a given city name using Geocoding API.
     * @param cityName Name of the city to search
     * @return JSONObject containing location data, or null if city not found
     */
    private static JSONObject getCityCoordinates(String cityName) throws Exception {
        // Encode city name for URL and build API request
        String encodedCity = URLEncoder.encode(cityName, "UTF-8");
        String apiUrl = GEOCODING_API + "?name=" + encodedCity + "&count=1&language=en&format=json";
        
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check if API request succeeded (HTTP 200)
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }

        // Read and parse JSON response
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            // Validate response contains results
            if (!jsonResponse.has("results")) {
                return null;
            }
            
            JSONArray results = jsonResponse.getJSONArray("results");
            return results.length() > 0 ? results.getJSONObject(0) : null;
        }
    }

    /**
     * Fetches weather data for given coordinates using Weather API.
     * @param latitude Latitude of the location
     * @param longitude Longitude of the location
     * @return JSONObject containing weather data, or null if request fails
     */
    private static JSONObject getWeatherData(double latitude, double longitude) throws Exception {
        // Build API URL with coordinates
        String apiUrl = String.format("%s?latitude=%.4f&longitude=%.4f&current_weather=true",
                                    WEATHER_API, latitude, longitude);
        
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.err.println("Weather API request failed");
            return null;
        }

        // Parse JSON weather data
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            
            return new JSONObject(response.toString());
        }
    }

    /**
     * Displays weather information in a user-friendly format.
     * @param weatherData JSON data from weather API
     * @param city Name of the city
     * @param country Name of the country
     */
    private static void displayWeather(JSONObject weatherData, String city, String country) {
        // Extract weather metrics from JSON
        JSONObject currentWeather = weatherData.getJSONObject("current_weather");
        double temperature = currentWeather.getDouble("temperature");
        double windSpeed = currentWeather.getDouble("windspeed");
        int windDirection = currentWeather.getInt("winddirection");
        int isDay = currentWeather.getInt("is_day");
        String time = currentWeather.getString("time");

        // Display formatted output
        System.out.println("\nCurrent Weather Details");
        System.out.println("----------------------");
        System.out.println("Location: " + city + ", " + country);
        System.out.println("Time: " + time);
        System.out.printf("Temperature: %.1f°C%n", temperature);
        System.out.printf("Wind Speed: %.1f km/h%n", windSpeed);
        System.out.printf("Wind Direction: %s%n", getWindDirection(windDirection));
        System.out.println("Daylight: " + (isDay == 1 ? "Daytime" : "Nighttime"));
        
        // Add human-readable weather interpretation
        System.out.println("\nWeather Interpretation:");
        System.out.println(getWeatherInterpretation(temperature, windSpeed));
    }

    /**
     * Converts wind direction in degrees to compass direction.
     * @param degrees Wind direction in degrees (0-360)
     * @return Compass direction (e.g., "Northwest")
     */
    private static String getWindDirection(int degrees) {
        String[] directions = {"North", "Northeast", "East", "Southeast", 
                              "South", "Southwest", "West", "Northwest"};
        return directions[(int) Math.round(((degrees % 360) / 45)) % 8];
    }

    /**
     * Generates a human-readable weather interpretation based on temperature and wind speed.
     * @param temp Temperature in °C
     * @param windSpeed Wind speed in km/h
     * @return Interpretation string (e.g., "Warm with Light winds")
     */
    private static String getWeatherInterpretation(double temp, double windSpeed) {
        StringBuilder interpretation = new StringBuilder();
        
        // Temperature interpretation
        if (temp > 40) interpretation.append("Extreme heat");
        else if (temp > 35) interpretation.append("Very hot");
        else if (temp > 25) interpretation.append("Warm");
        else if (temp > 15) interpretation.append("Pleasant");
        else if (temp > 5) interpretation.append("Cool");
        else interpretation.append("Cold");
        
        // Wind interpretation
        if (windSpeed > 20) interpretation.append(" with Strong winds");
        else if (windSpeed > 10) interpretation.append(" with Moderate breeze");
        else interpretation.append(" with Light winds");
        
        return interpretation.toString();
    }
}
