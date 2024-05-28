/**
 * Class containing constants, methods for checking collision between game objects , drawing the display and game objects, and running the game, replays if the user wants
 * @author Ahmet Eren Aslan, Student ID: 2021400126
 * @since Date: 16.04.2023
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Environment {
    public static final int CANVAS_WIDTH = 800;// Width of the canvas
    public static final int CANVAS_HEIGHT = 500;// Height of the canvas
    public static final double SCALE_X = 16;// Horizontal scale of the game and bar
    public static final double SCALE_Y_GAME = 9;// Vertical scale of the game
    public static final double SCALE_Y_BAR = -1;// Vertical scale of the bar
    public static final int TOTAL_GAME_DURATION = 40000;// Time of one game in milliseconds
    public static final int PAUSE_DURATION = 15;// Pause between in each frame in milliseconds

    /**
     * Default constructor of the environment class
     */
    public Environment() {}

    public Player player = new Player(Environment.SCALE_X/2);// Initializing the player object
    public Bar bar = new Bar();// Initializing the bar object
    public ArrayList<Ball> balls = new ArrayList<>();// Initializing the ball arraylist
    public ArrayList<Arrow> arrows = new ArrayList<>();// Initializing the arrow arraylist

    /**
     * Draws the background using StdDraw library
     */
    public static void displayBackground() {
        StdDraw.picture(Environment.SCALE_X/2,Environment.SCALE_Y_GAME/2,"background.png",Environment.SCALE_X,Environment.SCALE_Y_GAME);
    }


    /**
     * Moves the player using keyboard input
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void movePlayers(long dT) {
        // Checks for left arrow input
        if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            player.moveLeft(dT);
        }
        // Checks for right arrow input
        else if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            player.moveRight(dT);
        }
    }


    /**
     * Moves all the balls
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void moveBalls(long dT) {
        for(Ball ball: balls){
            ball.moveBall(dT);
        }
    }

    /**
     * Moves the arrow
     * @param dT Time passed between last frame and the current frame in milliseconds
     */
    public void moveArrows(long dT) {
        for (Arrow arrow:arrows) {
            if(Arrow.isActive())// Checks the activity of the arrow
                arrow.moveArrow(dT);
        }
    }

    /**
     * Checks for collisions between balls and arrow, and if exists, splits the ball that collide into two balls of one level down unless the ball is not the lowest level, then makes the arrow disappear
     */
    public void ballArrowCollision(){

        for (int ballIndex = 0; ballIndex < balls.size();ballIndex++) {
            Ball currentBall = new Ball(balls.get(ballIndex).getX(),balls.get(ballIndex).getY(),balls.get(ballIndex).getCurrentLevel(),1);// Makes a clone of the current checked ball
            // Checks if the arrow exist
            if (!arrows.isEmpty()) {
                // Checks if the arrow is active
                if(Arrow.isActive()) {
                    // Checks the horizontal distance between ball and arrow
                    if(Math.abs(arrows.get(0).getStartingPosition()- currentBall.getX()) < currentBall.getRadius()){
                        // Checks if the ball and the arrow which taken as a line is colliding
                        if(arrows.get(0).getArrowLength() > currentBall.getY() - Math.sqrt(Math.pow(currentBall.getRadius(),2))-Math.pow(arrows.get(0).getStartingPosition()-currentBall.getX(),2)) {
                            // Checks the level of the ball
                            if (currentBall.getCurrentLevel() > 0) {
                                // Creates two new balls of one level down
                                balls.add(new Ball(currentBall.getX(), currentBall.getY(), currentBall.getCurrentLevel() - 1,1));
                                balls.add(new Ball(currentBall.getX(), currentBall.getY(), currentBall.getCurrentLevel() - 1,-1));
                            }
                            balls.remove(ballIndex);// Makes the colliding ball disappear
                            // Makes the arrow disappear
                            Arrow.setActive(false);
                            arrows.clear();
                            return; // Exits the method
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks for collisions between player and balls, and if exists, makes game end
     */
    public void ballPlayerCollision(){
        for (Ball ball: balls){
            // Checks the horizontal distance between the player and ball
            if(Math.abs(player.getX()-ball.getX()) < ball.getRadius()+Player.WIDTH/2){
                // Checks if the ball is not directly on top of the player
                if(Math.abs(player.getX()-ball.getX())-Player.WIDTH/2 > 0){
                    // Checks if the ball collide with the player which is taken as a rectangle
                    if(Math.pow(Math.abs(player.getX()-ball.getX())-Player.WIDTH/2,2) + Math.pow(ball.getY()-Player.HEIGHT,2) < Math.pow(ball.getRadius(),2))
                        gameEnd(0);
                }
                // Checks if the ball is directly on top of the player
                else{
                    // Checks if the ball collide with the player which is taken as a rectangle
                    if(ball.getY()-Player.HEIGHT < ball.getRadius())
                        gameEnd(0);
                }
            }
        }
    }

    /**
     * Draws the endgame screen and using keyboard input runs the game again or exits from the game
     * @param endStatus 1 if the player win the game, 0 if the game is over
     */
    public void gameEnd(int endStatus){
        // Draws the background and remaining objects except balls and the arrow
        StdDraw.clear();
        displayBackground();
        bar.drawBar();
        player.drawPlayer();
        StdDraw.picture(Environment.SCALE_X/2, Environment.SCALE_Y_GAME/2.18,"game_screen.png",Environment.SCALE_X/3.8,Environment.SCALE_Y_GAME/4);
        // Sets the pencil
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,30));
        // Checks the result of the game and write the result
        if(endStatus == 0)
            StdDraw.text(Environment.SCALE_X/2, Environment.SCALE_Y_GAME/2.0,"Game Over!");
        else if (endStatus == 1)
            StdDraw.text(Environment.SCALE_X/2, Environment.SCALE_Y_GAME/2.0,"You Won!");
        // Writes the information about next choice of the player
        StdDraw.setFont(new Font("Helvetica",Font.BOLD,15));
        StdDraw.text(Environment.SCALE_X/2, Environment.SCALE_Y_GAME/2.3,"To Replay Click “Y”");
        StdDraw.text(Environment.SCALE_X/2, Environment.SCALE_Y_GAME/2.6,"To Quit Click “N”");

        StdDraw.pause(50);
        StdDraw.show();

        // Applies the command taken by the player
        while (true){
            if(StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                playGame();// Starts the game again
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                System.exit(1);// Exits the game
            }
        }
    }

    /**
     * Runs the game
     */
    public void playGame(){
        // Setting the objects for the start of the game
        bar = new Bar();
        balls.clear();
        arrows.clear();
        player.setX(Environment.SCALE_X/2);
        Arrow.setActive(false);
        for(int i=0; i<3; i++){
            balls.add(new Ball(Environment.SCALE_X/(4-i%2),0.5,i, (int) Math.pow(-1,i)));
        }

        long dT;// Time passed between last frame and the current frame in milliseconds
        Bar.setStartingTime(System.currentTimeMillis());// Setting the bars start time to current time
        long currentTime;// Time measured at the start of the frame

        // Main animation
        while(System.currentTimeMillis() - Bar.getStartingTime() < Environment.TOTAL_GAME_DURATION){
            currentTime = System.currentTimeMillis();// Measure the time at the start of the frame
            StdDraw.pause(Environment.PAUSE_DURATION);

            // Draws the current state of the board
            StdDraw.clear();
            displayBackground();
            bar.drawBar();
            if(!arrows.isEmpty()) {
                arrows.get(0).drawArrow();
                // Checks if the arrow reached the top
                if (arrows.get(0).isReached()){
                    Arrow.setActive(false);// Makes the arrow inactive
                    arrows.clear();
                }
            }
            player.drawPlayer();
            for (Ball ball:balls) {
                ball.drawBall();
            }
            StdDraw.show();

            // Takes keyboard input from user for arrow
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                // Checks if there is not an arrow in the canvas
                if(!Arrow.isActive())
                    arrows.add(new Arrow(player.getX()));
            }

            // Checks collisions and does the required actions
            ballArrowCollision();
            ballPlayerCollision();


            dT = (System.currentTimeMillis() - currentTime);// Time passed between last frame and the current frame in milliseconds

            // Moves the objects
            moveArrows(dT);
            moveBalls(dT);
            movePlayers(dT);

            // Checks the winning condition
            if(balls.isEmpty()){
                gameEnd(1);
            }
        }
        // Game overs if the time runs out
        gameEnd(0);
    }




}
