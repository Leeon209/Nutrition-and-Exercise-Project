public class KelvinTempSensorAdapter implements ITempSensor {
    private KelvinTempSensor kelvinSensor;
    private final int K_TO_C = -27315 ;         // Kelvin to Celsius
    private final double C_TO_F = (9.0 / 5.0) ; // Celsius to Fahr. factor
    private int currentReading = 0;

    // Constructor to inject the KelvinTempSensor
    public KelvinTempSensorAdapter(KelvinTempSensor kelvinSensor) {
        this.kelvinSensor = kelvinSensor;
    }

    @Override
    public void updateReading(){
        currentReading = kelvinSensor.reading();
    }

    // Convert Kelvin to Celsius and return the value
    @Override
    public double getCelsius() {
        return (currentReading + K_TO_C) / 100.0 ;
    }

    // Convert Kelvin to Fahrenheit and return the value
    @Override
    public double getFahrenheit() {
        return getCelsius() * C_TO_F + 32.0 ;
    }

    // Convert Kelvin to Fahrenheit and return the value
    @Override
    public double getKelvin() {
        return currentReading / 100.0 ;

    }

}
