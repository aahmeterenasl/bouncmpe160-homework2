/**
 * Class responsible methods for finding the time passed from the start of each game, and drawing the bar
 * @author Ahmet Eren Aslan, Student ID: 2021400126
 * @since Date: 16.04.2023
 */
import java.awt.*;
public class Bar {
    private static long startingTime;// Time calculated at the start of each game
    private double length;// Length of the bar

    /**
     * Calculates the time passed in milliseconds since the start of current game and returns it
     * @return The time passed since the start of current game
     */
    private int getCurrentTime(){
        return (int)(System.currentTimeMillis() - startingTime);
    }

    /**
     * Returns the time at the start of the current game
     * @return the time at the start of the current game
     */
    public static long getStartingTime() {
        return startingTime;
    }

    /**
     * Changes the time at the start of the current game
     * @param startingTime New time at the start of the current game
     */
    public static void setStartingTime(long startingTime) {
        Bar.startingTime = startingTime;
    }

    /**
     * Calculates and returns the current color of the bar
     * @return the current color of the bar
     */
    private Color getCurrentColor(){
        if(225-getCurrentTime()*225/Environment.TOTAL_GAME_DURATION >0)
            return new Color(225,225-getCurrentTime()*225/Environment.TOTAL_GAME_DURATION,0);
        else
            return new Color(225,225,0);
    }

    /**
     * Calculates and returns the current length of the bar
     * @return the current length of the bar
     */
    public double getCurrentLength(){
        if (Environment.SCALE_X - getCurrentTime()*Environment.SCALE_X/Environment.TOTAL_GAME_DURATION > 0)
            return Environment.SCALE_X - getCurrentTime()*Environment.SCALE_X/Environment.TOTAL_GAME_DURATION;
        return 0;
    }

    /**
     * Draws the bar using StdDraw library
     */
    public void drawBar(){
        StdDraw.picture(Environment.SCALE_X/2,Environment.SCALE_Y_BAR/2,"bar.png",Environment.SCALE_X,-Environment.SCALE_Y_BAR);
        StdDraw.setPenColor(getCurrentColor());
        length = getCurrentLength();
        StdDraw.filledRectangle( length/2,Environment.SCALE_Y_BAR/2,length/2,-Environment.SCALE_Y_BAR/4);
    }
}