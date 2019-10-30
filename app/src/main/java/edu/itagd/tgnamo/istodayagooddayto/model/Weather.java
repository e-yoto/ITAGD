package edu.itagd.tgnamo.istodayagooddayto.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Weather {
    int id;
    String weather;
    String weatherDesc;
    long temperature;
    String humidity;
    String pressure;
    String sunrise;
    String sunset;
    String city;
    String country;



    public Weather(){
    }

    public Weather(int id, String weather, String weatherDesc, long temperature, String humidity, String pressure, String sunrise, String sunset, String city, String country) {
        this.id = id;
        this.weather = weather;
        this.weatherDesc = weatherDesc;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.city = city;
        this.country = country;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature - (long)273.15;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = timeStampConvert(sunrise);
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = timeStampConvert(sunset);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the current date
     * @return date
     */
    public static String getDate(){
        LocalDate localDate = LocalDate.now();
        return DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(localDate);
    }

    /**
     * Returns the current time
     * @return
     */
    public static String getTime(){
        LocalTime localTime = LocalTime.now();
        return DateTimeFormatter.ofPattern("HH:mm:ss").format(localTime);
    }

    /**
     * Converts millisecond to date
     * @param millisecond
     * @return
     */
    public String timeStampConvert(long millisecond){
        Date date = new Date(millisecond*1000L);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
    }
}
