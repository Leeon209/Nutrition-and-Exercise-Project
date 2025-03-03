public interface ITempSensor {
    double getCelsius();
    double getKelvin();
    double getFahrenheit();

    void updateReading();
}
