/**
 * Class responsible for moving and drawing the player
 * @author Ahmet Eren Aslan, Student ID: 2021400126
 * @since Date: 16.04.2023
 */
public class Player {
    public static final double HEIGHT = 1.0/8 * Environment.SCALE_Y_GAME;// Height of the player in scale
    public static final double WIDTH = 23.0/37.0 * HEIGHT;// Width of the player in scale
    private static final double Y = HEIGHT/2;// Y coordinate of the player
    private static final double SPEED = Environment.SCALE_X/6000 ;// Horizontal speed of the player
    private double x;// X coordinate of the player

    /**
     * Constructor of player class
     * @param x x coordinate of the player
     */
    public Player(double x) {
        this.x = x;
    }

    /**
     * Returns the x coordinate of the player
     * @return Current x coordinate of the player
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the player
     * @return Current y coordinate of the player
     */
    public double getY() {
        return Y;
    }

    /**
     * Changes the x coordinate of the player
     * @param x New x coordinate of the player
     */
    public void setX(double x) {
        if((x>=0 + WIDTH/2) && (x<= Environment.SCALE_X - WIDTH/2))// Checks the new x value is in the canvas
            this.x = x;
    }

    /**
     * Moves the player to the left with constant horizontal speed
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void moveLeft(long dT){
        if(!(x - WIDTH*0.5  <0))// Checks whether the player is in the canvas
            this.x -= SPEED*dT;
    }

    /**
     * Moves the player to the right with constant horizontal speed
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void moveRight(long dT){
        if(!(x + WIDTH*0.5  >Environment.SCALE_X))// Checks whether the player is in the canvas
            this.x += SPEED*dT;
    }

    /**
     * Draws the player using StdDraw library
     */
    public void drawPlayer() {
        StdDraw.picture(getX(),getY(),"player_back.png",WIDTH,HEIGHT);
    }


}

