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
package lostmazed;

import java.awt.event.KeyEvent;
import lostmazed.game.Player;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lostmazed.game.Maze;
import soga2d.GraphicBoard;
import soga2d.events.KeyPressListener;

/**
 * The main game class.
 * 
 * @author Matúš Sulír
 */
public class Game {
    /**
     * The game graphic board width.
     */
    public static final int WIDTH = 800;
    
    /**
     * The game graphic board height.
     */
    public static final int HEIGHT = 600;
    
    private GraphicBoard board;
    private Maze maze;
    private Player player;
    private Menu menu;
    
    /**
     * The game constructor.
     * @param board the game graphic board
     * @param menu the game menu
     */
    public Game(GraphicBoard board, Menu menu) {
        this.board = board;
        this.menu = menu;
    }
    
    /**
     * Loads the game graphics and starts the game.
     */
    public void start() {
        board.clear();
        
        try {
            maze = new Maze(board);
            player = new Player(board);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        board.setKeyPressListener(new KeyPressListener() {
            @Override
            public void onKeyPress(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
                    pause();
            }
        });
    }
    
    /**
     * Pauses the game and shows the menu.
     */
    public void pause() {
        menu.show();
    }
}
