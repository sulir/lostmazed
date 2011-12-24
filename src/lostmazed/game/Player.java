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
import soga2d.GraphicObject;
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
        }, 50, 50);
    }
    
    private void onTimer() {
        tryToMove(xSpeed, ySpeed, maze.getMaze());
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
                newSpeed = 4;
            
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
    
    private void tryToMove(int deltaX, int deltaY, GraphicObject colliding) {
        board.lock();
        
        int xStep = Integer.signum(deltaX);
        
        for (int x = xStep; x != deltaX; x += xStep) {
            image.moveBy(xStep, 0);
            
            if (image.collidesWith(colliding)) {
                image.moveBy(-xStep, 0);
                break;
            }
        }
        
        int yStep = Integer.signum(deltaY);
        
        for (int y = yStep; y != deltaY; y += yStep) {
            image.moveBy(0, yStep);
            
            if (image.collidesWith(colliding)) {
                image.moveBy(0, -yStep);
                break;
            }
        }
        
        board.unlock();
    }
}
