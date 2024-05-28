/**
 * Class responsible for moving and drawing the arrow, and checking the activity of the arrow, an arrow cannot be initialized if the arrow is active.
 * @author Ahmet Eren Aslan, Student ID: 2021400126
 * @since Date: 16.04.2023
 */
public class Arrow {
    private static boolean active = false;// Variable showing whether there is an arrow in the canvas
    private double startingPosition;// X coordinate of the arrow
    private double arrowLength;// Y coordinate of the highest point of the arrow
    private static final double ARROW_SPEED = Environment.SCALE_Y_GAME/1500;// Vertical speed of the arrow

    /**
     * Constructor of the arrow class
     * @param startingPosition X coordinate of the arrow
     */
    public Arrow(double startingPosition) {
        active = true;// Unable creation of another arrow object
        this.startingPosition = startingPosition;
        this.arrowLength = Player.HEIGHT/2;
    }

    /**
     * Returns the activity of the arrow
     * @return the activity of the arrow
     */
    public static boolean isActive() {
        return active;
    }

    /**
     * Changes the activity of the arrow
     * @param active new activity of the arrow
     */
    public static void setActive(boolean active) {
        Arrow.active = active;
    }

    /**
     * Returns the X coordinate of the arrow
     * @return the X coordinate of the arrow
     */
    public double getStartingPosition() {
        return startingPosition;
    }

    /**
     * Returns the Y coordinate of the highest point of the arrow
     * @return Y coordinate of the highest point of the arrow
     */
    public double getArrowLength() {
        return arrowLength;
    }

    /**
     * Moves the player to the left with constant vertical speed
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void moveArrow(long dT) {
        arrowLength += ARROW_SPEED*dT;
    }

    /**
     * Checks if arrow reached the top of the canvas
     * @return true if arrow reached top, false otherwise
     */
    public boolean isReached() {
        if (arrowLength >= Environment.SCALE_Y_GAME)
            return true;
        else
            return false;
    }

    /**
     * Draws the arrow using StdDraw library
     */
    public void drawArrow() {
        StdDraw.picture(startingPosition,arrowLength/2,"arrow.png",0.1,arrowLength);
    }


}
