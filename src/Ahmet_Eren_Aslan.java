/**
 * Program runs the game by initializing environment
 * @author Ahmet Eren Aslan, Student ID: 2021400126
 * @since Date: 16.04.2023
 */
public class Ahmet_Eren_Aslan {
    /**
     * Creates the canvas and runs the game
     * @param args
     */
    public static void main(String[] args) {

        StdDraw.setCanvasSize(Environment.CANVAS_WIDTH,Environment.CANVAS_HEIGHT);// Creating canvas and setting its size
        StdDraw.setXscale(0,Environment.SCALE_X);// Scaling the canvas
        StdDraw.setYscale(Environment.SCALE_Y_BAR,Environment.SCALE_Y_GAME);// Scaling the canvas
        StdDraw.enableDoubleBuffering();

        Environment environment = new Environment();// initializing an environment object
        environment.playGame();// running the game

    }
}
