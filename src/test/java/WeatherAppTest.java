import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAppTest {

    // Tests for function convertWeatherCode
    @Test
    void test0ShouldReturnClear() {
        assertEquals("Clear", WeatherApp.convertWeatherCode(0));
    }
    @Test
    void test2ShouldReturnCloudy() {
        assertEquals("Cloudy", WeatherApp.convertWeatherCode(2));
    }
    @Test
    void test3ShouldReturnCloudy() {
        assertEquals("Cloudy", WeatherApp.convertWeatherCode(3));
    }
    @Test
    void test4ShouldReturnNull() {
        assertNull(null, WeatherApp.convertWeatherCode(4));
    }
    @Test
    void test51ShouldReturnRain() {
        assertEquals("Rain", WeatherApp.convertWeatherCode(51));
    }
    @Test
    void test67ShouldReturnRain() {
        assertEquals("Rain", WeatherApp.convertWeatherCode(67));
    }
    @Test
    void test60ShouldReturnRain() {
        assertEquals("Rain", WeatherApp.convertWeatherCode(60));
    }
    @Test
    void test80ShouldReturnRain() {
        assertEquals("Rain", WeatherApp.convertWeatherCode(80));
    }
    @Test
    void test99ShouldReturnRain() {
        assertEquals("Rain", WeatherApp.convertWeatherCode(99));
    }
    @Test
    void test90ShouldReturnRain() {
        assertEquals("Rain", WeatherApp.convertWeatherCode(90));
    }
    @Test
    void test71ShouldReturnSnow() {
        assertEquals("Snow", WeatherApp.convertWeatherCode(71));
    }
    @Test
    void test77ShouldReturnSnow() {
        assertEquals("Snow", WeatherApp.convertWeatherCode(77));
    }
    @Test
    void test75ShouldReturnSnow() {
        assertEquals("Snow", WeatherApp.convertWeatherCode(75));
    }

    // Test for function getCurrentTime
    @Test
    void testLocalDateTimeNowShouldReturnGoodFormat() {
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'")), WeatherApp.getCurrentTime(LocalDateTime.now()));
    }

    // Tests for function findIndexOfCurrentTime
    String TimeNow = WeatherApp.getCurrentTime(LocalDateTime.now());
    JSONArray list = new JSONArray();
    @Test
    void listShouldReturn3() {
        list.addAll(List.of("wrongValue1", "wrongValue2", "wrongValue3", TimeNow, "wrongValue4", "wrongValue5"));
        assertEquals(3, WeatherApp.findIndexOfCurrentTime(list));
    }
    @Test
    void listShouldReturn0() {
        list.addAll(List.of("wrongValue1", "wrongValue2", "wrongValue3", "wrongValue4", "wrongValue5", "wrongValue6"));
        assertEquals(0, WeatherApp.findIndexOfCurrentTime(list));
    }
}