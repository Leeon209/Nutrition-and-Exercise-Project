/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * Class for a simple computer based weather station that reports the current
 * temperature (in Celsius) every second. The station is attached to a
 * sensor that reports the temperature as a 16-bit number (0 to 65535)
 * representing the Kelvin temperature to the nearest 1/100th of a degree.
 *
 * This class is implements Runnable so that it can be embedded in a Thread
 * which runs the periodic sensing.
 *
 * The class also extends Observable so that it can notify registered
 * objects whenever its state changes. Convenience functions are provided
 * to access the temperature in different schemes (Celsius, Kelvin, etc.)
 */
import java.util.Observable ;
public class WeatherStation extends Observable implements Runnable {

    private final ITempSensor tempSensor ; // Temperature sensor.
    private final IBarometer barometer; // Barometer;

    private final long PERIOD = 1000 ;      // 1 sec = 1000 ms


    private double currentBarometricPressure; // Current barometric Pressure from barometer

    /*
     * When a WeatherStation object is created, it in turn creates the sensor
     * object it will use.
     */
    public WeatherStation(ITempSensor tempSensor, IBarometer barometer) {
        this.tempSensor = tempSensor;
        this.barometer = barometer;
        //currentReading = (int) (tempSensor.getTemperatureInCelsius() * 100 + 27315); // Initialize in Kelvin
        //currentBarometricPressure = barometer.getPressureInches();  // Initialize pressure
    }

    /*
     * The "run" method called by the enclosing Thread object when started.
     * Repeatedly sleeps a second, acquires the current temperature from its
     * sensor, and notifies registered Observers of the change.
     */
    public void run() {
        while( true ) {
            try {
                Thread.sleep(PERIOD) ;
            } catch (Exception e) {}    // ignore exceptions

            /*
             * Get next reading and notify any Observers.
             */
            synchronized (this) {
                currentBarometricPressure = barometer.pressure();  // Update pressure
                tempSensor.updateReading();
            }
            setChanged() ;
            notifyObservers() ;
        }
    }

    /*
     * Return the current reading in degrees celsius as a
     * double precision number.
     */
    public synchronized double getCelsius() {
        return tempSensor.getCelsius() ;
    }

    /*
     * Return the current reading in degrees Kelvin as a
     * double precision number.
     */
    public synchronized double getKelvin() {
        return tempSensor.getKelvin();
    }

    /*Return the current reading in degrees Fahrenheit double precision number. */
    /* Celsius math then Fahrenheit */
    /* Source: https://www.thoughtco.com/celcius-to-farenheit-formula-609227 */
    public synchronized double getFahrenheit() {
        return tempSensor.getFahrenheit();
    }

    /* Source for inches and millibars of Mercury pressures
     * https://www.sensorsone.com/inhg-to-mbar-conversion-table/#:~:text=mbar%20value%20%3D%20inHg%20value%20x%2033.8639
     */
    
    //  inches of Mercury
    public synchronized double getPressureInches() {
        return currentBarometricPressure;
    }
    // millibars of Mercury
    public synchronized double getPressureMillibars() {
        return currentBarometricPressure * 33.8639;
    }
}
