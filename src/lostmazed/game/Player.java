/*
 * Lostmazed
 *
 * Copyright 2011 Matúš Sulír.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lostmazed.game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import lostmazed.Game;
import soga2d.GraphicBoard;
import soga2d.Picture;
import soga2d.events.KeyListener;

/**
 * The player who wants to get out of the maze.
 * @author Matúš Sulír
 */
public class Player {
    private GraphicBoard board;
    private Picture image;
    private Maze maze;
    private int xSpeed;
    private int ySpeed;
    
    /**
     * Loads the graphics, initializes the player and adds him to the board.
     * @param board the board to add the player to
     * @throws IOException when the image can not be loaded
     */
    public Player(GraphicBoard board, Maze maze) throws IOException {
        this.board = board;
        this.maze = maze;
        
        image = new Picture("lostmazed/img/player.png");
        board.addObject(image);

        image.setKeyListener(new KeyListener() {
            @Override
            public void onKeyEvent(KeyEvent event) {
                onKey(event);
            }
        });
        
        image.moveTo(Game.WIDTH / 2, Game.HEIGHT / 2);
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                onTimer();
            }
        }, 20, 20);
    }
    
    /**
     * Called periodically to move the player if needed.
     */
    private void onTimer() {
        tryToMove(xSpeed, ySpeed);
    }
    
    /**
     * React to key events by moving the player.
     * @param event the key event
     */
    private void onKey(KeyEvent event) {
        int eventType = event.getID();
        
        if (eventType == KeyEvent.KEY_PRESSED || eventType == KeyEvent.KEY_RELEASED) {
            int newSpeed = 0;
            
            if (eventType == KeyEvent.KEY_PRESSED)
                newSpeed = 2;
            
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    ySpeed = -newSpeed;
                    break;
                case KeyEvent.VK_DOWN:
                    ySpeed = newSpeed;
                    break;
                case KeyEvent.VK_LEFT:
                    xSpeed = -newSpeed;
                    break;
                case KeyEvent.VK_RIGHT:
                    xSpeed = newSpeed;
                    break;
            }
        }
    }
    
    /**
     * Moves the player if there is no wall in the current direction.
     * 
     * If there is a wall, moves the player as close to the wall as possible.
     * @param deltaX the number of pixels to move in x direction
     * @param deltaY the number of pixels to move in y direction
     */
    private void tryToMove(int deltaX, int deltaY) {
        if (deltaX != 0 || deltaY != 0) {
            board.lock();

            updateAngle();

            int xStep = Integer.signum(deltaX);

            for (int x = xStep; x != deltaX; x += xStep) {
                image.moveBy(xStep, 0);

                if (image.collidesWith(maze.getMaze())) {
                    image.moveBy(-xStep, 0);
                    break;
                }
            }

            int yStep = Integer.signum(deltaY);

            for (int y = yStep; y != deltaY; y += yStep) {
                image.moveBy(0, yStep);

                if (image.collidesWith(maze.getMaze())) {
                    image.moveBy(0, -yStep);
                    break;
                }
            }

            board.unlock();
        }
    }
    
    /**
     * Updates the orientation of the image according to the current moving
     * direction.
     */
    private void updateAngle() {
        // add one to the direction (-1, 0 or 1) obtain an array index
        int xDirection = Integer.signum(xSpeed) + 1;
        int yDirection = Integer.signum(ySpeed) + 1;
        
        int[][] angles = {
            {-45, 0, 45},
            {-90, -1, 90},
            {-135, 180, 135}
        };
        
        int newAngle = angles[yDirection][xDirection];
        
        if (newAngle != -1)
            image.setAngle(newAngle);
    }
}
