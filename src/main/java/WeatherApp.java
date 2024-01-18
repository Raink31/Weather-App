import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Retrieve Weather data from API. The GUI will display this data to the user.
public class WeatherApp {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApp.class);
    // Fetch weather data for given location
    public static JSONObject getWeatherData(String locationName) {
        // Get location coordinates using the geolocation API
        JSONArray locationData = getLocationData(locationName);

        // Extract latitude and longitude data
        assert locationData != null;
        JSONObject location = (JSONObject) locationData.getFirst();
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        // Build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m";

        try {
            // Call API and get response
            HttpURLConnection conn = fetchAPIResponse(urlString);

            // Check for response status - 200 = OK
            assert conn != null;
            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to the API");
                return null;
            }

            // Store resulting json data
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                // read and store into the string builder
                resultJson.append(scanner.nextLine());
            }

            // Close scanner
            scanner.close();

            // Close url connection
            conn.disconnect();

            // Parse through the data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            // Retrieve hourly data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

            // Get the index of the current hour
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            // Get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            // Get weather code
            JSONArray weatherCode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weatherCode.get(index));

            // Get humidity
            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            // Get wind speed
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            // build the weather json data for gui
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;

        } catch(Exception e) {
            logger.error("An error occurred.", e);
        }

        return null;
    }

    // Retrieves geographic coordinates for given location name
    public static JSONArray getLocationData(String locationName) {
        // replace any whitespace in location name to + to adhere to the API's request format
        locationName = locationName.replaceAll(" ", "+");

        // Build API url with location parameter
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + locationName + "&count=10&language=en&format=json";

        try {
            // Call API and get a response
            HttpURLConnection conn = fetchAPIResponse(urlString);

            // Check response status 200 = OK
            assert conn != null;
            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            } else {
                // Store the API Results
                StringBuilder resultJSon = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());

                // Read and store the resulting json data into our string builder
                while (scanner.hasNext()) {
                    resultJSon.append(scanner.nextLine());
                }

                // Close scanner
                scanner.close();

                // Close url connection
                conn.disconnect();

                // parse the JSON string into a JSON obj
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJSon));

                // Get the list of location data the API generated from the location name
                return (JSONArray) resultsJsonObj.get("results");
            }

        } catch(Exception e) {
            logger.error("An error occurred.", e);
        }

        // Couldn't find location
        return null;
    }

    private static HttpURLConnection fetchAPIResponse(String urlString) {
        try {
            // Attempt to create connection
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection)  url.openConnection();

            // Set request method to get
            conn.setRequestMethod("GET");

            // Connect to the API
            conn.connect();
            return conn;
        } catch(IOException e) {
            logger.error("An error occurred.", e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // Could not make connection
        return null;
    }

    private static int findIndexOfCurrentTime(JSONArray timelist) {
        String currentTime = getCurrentTime();

        /// Iterate through the time list and see which one match the current time
        for (int i = 0; i < timelist.size(); i++) {
            String time = (String) timelist.get(i);
            if (time.equalsIgnoreCase(currentTime)) {
                // return the index
                return i;
            }
        }

        return 0;
    }

    public static String getCurrentTime() {
        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format date to be 2024-01-18T00:00 (How it is read on the API)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        // Format and print the current date and time

        return currentDateTime.format(formatter);
    }

    // Convert the weather code into readable text : See WMO Weather interpretation codes
    private static String convertWeatherCode(long weatherCode) {
        String weatherCondition = "";
        if (weatherCode == 0L) {
            // Clear
            weatherCondition = "Clear";
        } else if (weatherCode <= 3L && weatherCode > 0L) {
            // Cloudy
            weatherCondition = "Cloudy";
        } else if ((weatherCode >= 51L && weatherCode <= 67L)
                    || (weatherCode >= 80L && weatherCode <= 99L)) {
            // Rain
            weatherCondition = "Rain";
        } else if (weatherCode >= 71L && weatherCode <= 77L) {
            // Snow
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }
}
