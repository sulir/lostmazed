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
 *
 * @author Matúš Sulír
 */
public class Menu {
    private GraphicBoard board;
    
    public Menu(GraphicBoard board) {
        this.board = board;
    }
    
    public void show() {
        Font font = new Font("Arial", Font.BOLD, 20);
        
        Text newGame = new Text("NEW GAME");
        newGame.setFont(font);
        newGame.setColor(Color.BLUE);
        newGame.moveTo(400, 200);
        newGame.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                newGame();
            }
        });
        
        Text exit = new Text("EXIT");
        exit.setFont(font);
        exit.setColor(Color.BLUE);
        exit.moveTo(400, 250);
        exit.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                exit();
            }
        });
        
        board.lock();
        board.clear();
        
        board.addObject(newGame);
        board.addObject(exit);
        
        board.unlock();
    }
    
    private void newGame() {
        new Game(board).start();
    }
    
    private void exit() {
        System.exit(0);
    }
}
