/*
 * Initial Author
 *      Leeon Noun
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * Swing UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a Swing application.
 */

import java.awt.Font ;
import java.awt.GridLayout ;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

//import java.text.DecimalFormat ;
public class SwingUI extends JFrame implements Observer {
    private JLabel celsiusField ;   // put current celsius reading here
    private JLabel kelvinField ;    // put current kelvin reading here

    // activity3b
    private JLabel fahrenheitField ; // put current fahrenheit reading here
    private JLabel pressureInchesField; // put current pressure inches of mercury reading here
    private JLabel pressuremillibarsField; // put current pressure millibars of mercury reading here
    private WeatherStation weatherStation;
    // not Running private SwingUI properly;
    //private SwingUI swingUI;
    /*
     * A Font object contains information on the font to be used to
     * render text.
     */
    private static Font labelFont =
        new Font(Font.SERIF, Font.PLAIN, 72) ;

    /*
     * Create and populate the SwingUI JFrame with panels and labels to
     * show the temperatures.
     */
    public SwingUI(WeatherStation weatherStation) {
        super("Weather Station") ;
        // swingUI = new SwingUI(weatherStation);
        /*
         * WeatherStation frame is a grid of 1 row by an indefinite
         * number of columns.
         */

         /* Adding observer for WeatherStation and following TextUI structure*/
        this.weatherStation = weatherStation;
        weatherStation.addObserver(this);

        this.setLayout(new GridLayout(1,0)) ;

        /*
         * There are two panels, one each for Kelvin and Celsius, added to the
         * frame. Each Panel is a 2 row by 1 column grid, with the temperature
         * name in the first row and the temperature itself in the second row.
         */
        JPanel panel ;

        /*
         * Set up Kelvin display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Kelvin ", panel) ;
        kelvinField = createLabel("", panel) ;

        /*
         * Set up Celsius display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Celsius ", panel) ;
        celsiusField = createLabel("", panel) ;

        /*
         * Set up Fahrenheit display.
         */

        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Fahrenheit ", panel) ;
        fahrenheitField = createLabel("", panel) ;

        /*
         * Set up Pressure Inches of mercury display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Inches ", panel) ;
        pressureInchesField = createLabel("", panel) ;
         /*
         * Set up Pressure millibars of mercury display.
         */
        panel = new JPanel(new GridLayout(2,1)) ;
        this.add(panel) ;
        createLabel(" Millibars ", panel) ;
        pressuremillibarsField = createLabel("", panel) ;

         /*
         * Set up the frame's default close operation pack its elements,
         * and make the frame visible.
         */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.pack() ;
        this.setVisible(true) ;
    }

    /*
     * Set the label holding the Kelvin temperature.
     */
    public void setKelvinJLabel(double temperature) {
        kelvinField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Celsius temperature.
     */
    public void setCelsiusJLabel(double temperature) {
        celsiusField.setText(String.format("%6.2f", temperature)) ;
    }

    /*
     * Set the label holding the Fahrenheit temperature.
     */
    public void setFahrenheitJLabel(double temperature) {
        fahrenheitField.setText(String.format("%6.2f", temperature));
    }

    /*
     * Set the label holding the Inches of Mercury Pressure.
     */
    public void setInchesPressureJLabel(double pressure) {
        pressureInchesField.setText(String.format("%6.2f", pressure));
    }

    /*
     * Set the label holding the millibars of Mercury Pressure.
     */
    public void setMillibarsPressureJLabel(double pressure) {
        pressuremillibarsField.setText(String.format("%6.2f", pressure));
    }

    /*
     * Create a Label with the initial value <title>, place it in
     * the specified <panel>, and return a reference to the Label
     * in case the caller wants to remember it.
     */
    private JLabel createLabel(String title, JPanel panel) {
        JLabel label = new JLabel(title) ;

        label.setHorizontalAlignment(JLabel.CENTER) ;
        label.setVerticalAlignment(JLabel.TOP) ;
        label.setFont(labelFont) ;
        panel.add(label) ;

        return label ;
    }

    @Override
    public void update(Observable obs, Object arg) {
        /*
         * Check for spurious updates from unrelated objects.
         */
        if( weatherStation != obs ) {
            return ;
        }
        /*
         * Retrieve and display the temperatures in real time.
         */
            setCelsiusJLabel(weatherStation.getCelsius());
            setKelvinJLabel(weatherStation.getKelvin());
            setFahrenheitJLabel(weatherStation.getFahrenheit());
            setInchesPressureJLabel(weatherStation.getPressureInches());
            setMillibarsPressureJLabel(weatherStation.getPressureMillibars());
    }

    public static void main(String[] args) {
        ITempSensor tempSensor = new KelvinTempSensorAdapter(new KelvinTempSensor());
        IBarometer barometer = new Barometer();
        WeatherStation weatherStation = new WeatherStation(tempSensor, barometer);        
        Thread wsThread = new Thread(weatherStation);
        // avoid unused variable so put into comment line
        // SwingUI swinguI = new SwingUI(weatherStation);

        // new line to replace above 
        new SwingUI(weatherStation);
        wsThread.start();
    }
}
