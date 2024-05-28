/**
 * Class responsible for moving and drawing balls
 * @author Ahmet Eren Aslan, Student ID: 2021400126
 * @since Date: 16.04.2023
 */

public class Ball {
    private static final double GRAVITY = 0.00000033 * Environment.SCALE_Y_GAME;// Value of gravity in the game
    private static final double MIN_POS_HEIGHT = Player.HEIGHT * 1.4;// The maximum attainable height by the smallest ball
    private static final double MIN_POS_RADIUS = Environment.SCALE_Y_GAME * 0.0175;// The radius of the smallest ball
    private double x;// X coordinate of the  ball
    private double y;// Y coordinate of the  ball
    private double y0;// Y coordinate of the start of the last projectile motion
    private double velocityX ;// Horizontal speed of the ball
    private double velocityY;// Vertical speed of the player
    private int currentLevel;// Level of the ball
    private int currentTime;// Time passed from the start of the last projectile motion


    /**
     * Constructor of the ball class
     * @param x X coordinate of the  ball
     * @param y Y coordinate of the  ball
     * @param currentLevel Level of the ball
     * @param direction Direction of the ball in the horizontal axis, 1 for right and -1 for left
     */
    public Ball(double x, double y, int currentLevel,int direction) {
        this.x = x;
        this.y = y;
        this.y0 = y;
        this.currentLevel = currentLevel;
        this.currentTime = 0;
        velocityX = direction * (Environment.SCALE_X/Environment.TOTAL_GAME_DURATION) * 2.4;
        velocityY = Math.sqrt(2*GRAVITY*getMaxHeight());// Balls starts with a vertical velocity that make them travel its max height in the vertical axis
    }

    /**
     * Returns the x coordinate of the ball
     * @return Current X coordinate of the ball
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the ball
     * @return Current y coordinate of the ball
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the level of the ball
     * @return Current level of the ball
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Calculates the radius of the ball using its level and returns the value
     * @return Radius of the ball
     */
    public double getRadius(){
        return MIN_POS_RADIUS * Math.pow(2.0,currentLevel);
    }

    /**
     * Calculates the maximum height attainable by the ball using its level and returns the value
     * @return the maximum height attainable by the ball
     */
    public double getMaxHeight(){
        return MIN_POS_HEIGHT * Math.pow(1.75,currentLevel);
    }

    /**
     * Changes the staring point's y value of the projectile motion
     * @param y0 New stating point's y value
     */
    public void setY0(double y0) {
        if((y0 >= 0 + getRadius()) && (y0 <= Environment.SCALE_Y_GAME))// Checks the new y0 value is in the canvas
            this.y0 = y0;
    }

    /**
     * Moves the ball according to projectile motion rules
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void moveBall(long dT){
        currentTime += dT; // adds time passed between last frames to the total time passed from the start of projectile motion
        // Checks the ball collide with walls
        if((x-getRadius()+velocityX*dT<0)||(x+getRadius()+velocityX*dT>Environment.SCALE_X))
            velocityX *= -1;// make the horizontal speed's direction reverse
        // Checks the ball collide with bottom
        if(y0 + velocityY*currentTime - (GRAVITY*Math.pow(currentTime,2))/2 - getRadius() < 0) {
            setY0(getRadius());// make start point current point
            currentTime = 0;// make total time zero
        }
        x += velocityX*dT;
        y = y0 + velocityY*currentTime - (GRAVITY*Math.pow(currentTime,2))/2;


    }

    /**
     * Draws the ball using StdDraw library
     */
    public void drawBall(){
        StdDraw.picture(getX(),getY(),"ball.png",2*getRadius(),2*getRadius());
    }
}
