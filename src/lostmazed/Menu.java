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

import java.awt.Color;
import java.awt.Font;
import soga2d.GraphicBoard;
import soga2d.Text;
import soga2d.events.MouseClickListener;

/**
 * The main menu of the game.
 * @author Matúš Sulír
 */
public class Menu {
    private GraphicBoard board;
    private Text newGame;
    private Text exit;
    
    /**
     * Constructs the menu.
     * @param board the board to display the menu on
     */
    public Menu(GraphicBoard board) {
        this.board = board;
        
        Font font = new Font("Arial", Font.BOLD, 20);
        Color color = Color.BLUE;
        
        newGame = new Text("NEW GAME", 400, 200, font, color);
        newGame.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                newGame();
            }
        });
        
        exit = new Text("EXIT", 400, 250, font, color);
        exit.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                exit();
            }
        });
    }
    
    /**
     * Displays the menu.
     */
    public void show() {
        board.lock();
        board.clear();
        
        board.addObject(newGame);
        board.addObject(exit);
        
        board.unlock();
    }
    
    /**
     * Called when a user presses the "New Game" menu.
     */
    private void newGame() {
        new Game(board, this).start();
    }
    
    /**
     * Called when a user presses the "Exit" menu.
     */
    private void exit() {
        System.exit(0);
    }
}
