/*
 * Initial Author
 *      Leeon Noun
 *
 * Other Contributers
 *
 * Acknowledgements
 */
 
/*
 * The TextUI class is an observer of the WeatherStation that,
 * when it receives an update message, prints the temperature
 * in Celsius and Kelvin.
 *
 * The main method for the text based monitoring application
 * is here as well.
 */
import java.util.Observer ;
import java.util.Observable ;

public class TextUI implements Observer {
    private final WeatherStation station ;

    /*
     * Remember the station we're attached to and
     * add ourselves as an observer.
     */
    public TextUI(WeatherStation station) {
        this.station = station ;
        this.station.addObserver(this) ;
    }

    /*
     * Called when WeatherStation gets another reading.
     * The Observable should be the station; the Object
     * argument is ignored.
     */
    public void update(Observable obs, Object ignore) {
        /*
         * Check for spurious updates from unrelated objects.
         */
        if( station != obs ) {
            return ;
        }
        /*
         * Retrieve and print the temperatures.
         */

        /*Activity 3a codes*/
        // System.out.printf(
        //         "Reading is %6.2f degrees C and %6.2f degrees K%n",
        //         station.getCelsius(), station.getKelvin()) ;
        // New code and organized for temperature and pressures
        System.out.printf("Temperature: %6.2f C  %6.2f F  %6.2f K%n", station.getCelsius(), station.getFahrenheit(), station.getKelvin());
        System.out.printf("Pressure:    %6.2f inches %6.2f mbar%n%n", station.getPressureInches(), station.getPressureMillibars());
    }

    /*
     * Start the application.
     */
    public static void main(String[] args) {
        ITempSensor tempSensor = new KelvinTempSensorAdapter(new KelvinTempSensor());
        IBarometer barometer = new Barometer();

        WeatherStation ws = new WeatherStation(tempSensor, barometer) ;
        Thread thread = new Thread(ws) ;
        // avoid unused variable so put into comment line
        // TextUI ui = new TextUI(ws) ;

        // new line to replace above
        new TextUI(ws);
        thread.start() ;
    }
}
