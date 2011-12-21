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
import lostmazed.Game;
import soga2d.GraphicBoard;
import soga2d.Picture;
import soga2d.events.KeyPressListener;

/**
 * The player who wants to get out of the maze.
 * @author Matúš Sulír
 */
public class Player {
    private GraphicBoard board;
    private Picture image;
    
    /**
     * Loads the graphics, initializes the player and adds him to the board.
     * @param board the board to add the player to
     * @throws IOException when the image can not be loaded
     */
    public Player(GraphicBoard board) throws IOException {
        this.board = board;
        image = new Picture("lostmazed/img/player.png");
        
        board.addObject(image);

        image.setKeyPressListener(new KeyPressListener() {
            @Override
            public void onKeyPress(KeyEvent event) {
                move(event);
            }
        });
        
        image.moveTo(Game.WIDTH / 2, Game.HEIGHT / 2);
    }
    
    /**
     * React to key presses by moving the player.
     * @param event the key event
     */
    private void move(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                image.moveBy(0, -5);
                break;
            case KeyEvent.VK_DOWN:
                image.moveBy(0, 5);
                break;
            case KeyEvent.VK_LEFT:
                image.moveBy(-5, 0);
                break;
            case KeyEvent.VK_RIGHT:
                image.moveBy(5, 0);
                break;
        }
    }
}
