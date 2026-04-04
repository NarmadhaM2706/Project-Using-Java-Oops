abstract class Weather {
    private double temperature;
    private double humidity;
    public Weather(double temperature, double humidity) {
        setTemperature(temperature);
        setHumidity(humidity);
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        if (temperature < -100 || temperature > 100) {
            throw new IllegalArgumentException("Invalid temperature!");
        }
        this.temperature = temperature;
    }
    public double getHumidity() {
        return humidity;
    }
    public void setHumidity(double humidity) {
        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException("Invalid humidity!");
        }
        this.humidity = humidity;
    }
    abstract void display();
}
class CurrentWeather extends Weather {
    public CurrentWeather(double temp, double hum) {
        super(temp, hum);
    }
    @Override
    void display() {
        System.out.println("Current Weather:");
        System.out.println("Temperature: " + getTemperature());
        System.out.println("Humidity: " + getHumidity());
    }
}

class ForecastWeather extends Weather {
    private String prediction;
    public ForecastWeather(double temp, double hum, String prediction) {
        super(temp, hum);
        this.prediction = prediction;
    }

    @Override
    void display() {
        System.out.println("Forecast Weather:");
        System.out.println("Temperature: " + getTemperature());
        System.out.println("Humidity: " + getHumidity());
        System.out.println("Prediction: " + prediction);
    }
}
class AlertSystem {
    public void checkAlert(Weather weather) {
        if (weather.getTemperature() > 40) {
            System.out.println("⚠ Heat Alert!");
        } else if (weather.getTemperature() < 0) {
            System.out.println("⚠ Cold Alert!");
        }
    }
}
class WeatherStation {
    private String stationName;
    private String location;

    public WeatherStation(String name, String location) {
        this.stationName = name;
        this.location = location;
    }

    public void showDetails() {
        System.out.println("Station: " + stationName);
        System.out.println("Location: " + location);
    }

    public void displayWeather(Weather weather) {
        weather.display(); // Polymorphism
    }
}
public class WeatherApp {
    public static void main(String[] args) {

        try {
            WeatherStation station = new WeatherStation("Central Station", "Chennai");
            Weather current = new CurrentWeather(35, 60);
            Weather forecast = new ForecastWeather(42, 55, "Sunny");
            AlertSystem alert = new AlertSystem();
            station.showDetails();
            System.out.println("\n--- Current Data ---");
            station.displayWeather(current);
            alert.checkAlert(current);
            System.out.println("\n--- Forecast Data ---");
            station.displayWeather(forecast);
            alert.checkAlert(forecast);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}