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
import soga2d.objects.Text;
import soga2d.events.MouseClickListener;

/**
 * The main menu of the game.
 * @author Matúš Sulír
 */
public class MainMenu {
    private GraphicBoard board;
    private Text storyMode;
    private Text mazeCreator;
    private Text exit;
    
    /**
     * Constructs the menu.
     * @param board the board to display the menu on
     */
    public MainMenu(GraphicBoard board) {
        this.board = board;
        
        Font font = new Font("Arial", Font.BOLD, 20);
        Color color = Color.BLUE;
        
        storyMode = new Text("STORY MODE", 400, 200, font, color);
        storyMode.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                storyMode();
            }
        });
        
        mazeCreator = new Text("MAZE CREATOR", 400, 250, font, color);
        
        exit = new Text("EXIT", 400, 300, font, color);
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
        
        board.addObject(storyMode);
        board.addObject(mazeCreator);
        board.addObject(exit);
        
        board.unlock();
    }
    
    /**
     * Called when a user choosed the "Story Mode" menu.
     */
    private void storyMode() {
        new Story(board, this).show();
    }
    
    /**
     * Called when a user choosed the "Exit" menu.
     */
    private void exit() {
        System.exit(0);
    }
}
