package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class BarGraph extends JPanel{
    private int fat;
    private int carbs;
    private int protein;

    /**
    * initialize bargraph with fat, carbs, and protein values
    * @param fat Amount of fat (grams)
    * @param carbs Amount of carbohydrates (grams)
    * @param protein Amount of protein (grams)
    */
    public BarGraph(int fat, int carbs, int protein){
        this.fat = fat;
        this.carbs = carbs; 
        this.protein = protein;
    
        this.setPreferredSize(new Dimension(300,300));
        this.setMinimumSize(new Dimension(200,200));
    }

    /**
    * Paints the component to display the bar graph for fat, carbs, and protein
    * @param g Graphics object used for drawing
    */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        /**
        * Get the h/w of the panel
        * @param g Graphics object
        */
        Rectangle bounds = g.getClipBounds();
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();

        /**
        * get the width of each bar
        * @param w Width of the panel
        * @return Width of each bar
        */
        int barWidth = w / 3;

        /**
        * Find max value among fat, carbs, and protein 
        * @return max value among fat, carbs, and protein
        */
        int maxValue = Math.max(fat, Math.max(carbs, protein));

        /**
         * Set font to bold
         */
        Font boldFont = g.getFont().deriveFont(Font.BOLD);
        g.setFont(boldFont);

        if (maxValue > 0){

            /**
            * Draw the red fat bar
            * @param fatHeight height of the fat bar
            */
            int fatHeight = h * fat / maxValue;
            g.setColor(Color.RED);
            g.fillRect(0, h - fatHeight, barWidth, fatHeight);
            g.setColor(Color.BLACK);
            g.drawString("Fat: " + fat + "g", 10, h - 5);

            /**
            * Draw the green carbs bar
            * @param carbsHeight height of the carbs bar
            */
            int carbsHeight = h * carbs / maxValue;
            g.setColor(Color.GREEN);
            g.fillRect(barWidth, h - carbsHeight, barWidth, carbsHeight);
            g.setColor(Color.BLACK);
            g.drawString("Carbs: " + carbs + "g", barWidth + 10, h - 5);


           /**
            * Draw the blue protein bar
            * @param proteinHeight geight of the protein bar
            */
            int proteinHeight = h * protein / maxValue;
            g.setColor(Color.BLUE);
            g.fillRect(2 * barWidth, h - proteinHeight, barWidth, proteinHeight);
            g.setColor(Color.BLACK);
            g.drawString("Protein: " + protein + "g", 2 * barWidth + 10, h - 5);
        }
    }

    /**
    * Updates the values for fat, carbs, and protein, then rrepaints the bar graph
    * @param fat Amount of fat (grams)
    * @param carbs Amount of carbohydrates (grams)
    * @param protein Amount of protein (grams)
    */
    public void updateValues(int fat, int carbs, int protein){
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        repaint();
    }
}
