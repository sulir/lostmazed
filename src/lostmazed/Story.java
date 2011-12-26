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
import java.awt.event.KeyEvent;
import soga2d.GraphicBoard;
import soga2d.objects.Rectangle;
import soga2d.objects.Text;
import soga2d.events.KeyListener;

/**
 * The game story screen.
 * @author Matúš Sulír
 */
public class Story {
    private GraphicBoard board;
    private MainMenu menu;
    private String storyString = "One sunny day you decided to make a trip and visit a small hedge maze.\n"
            + "As you are walking, you suddenly observe stairs leading under the ground. You descend them\n"
            + "and find a system of underground corridors. After half an hour, you realize that you are...\n"
            + "lost in a maze!";
    
    /**
     * Constructs the story screen.
     * @param board the board to draw on
     * @param menu the game menu
     */
    public Story(GraphicBoard board, MainMenu menu) {
        this.board = board;
        this.menu = menu;
    }
    
    /**
     * Shows the story and waits for the user to press a key.
     */
    public void show() {
        board.lock();
        board.clear();
        
        Rectangle background = new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT, Color.BLACK, Color.BLACK);
        Text story = new Text(storyString, 30, 300, new Font("Arial", Font.BOLD, 16), Color.LIGHT_GRAY);
        Text pressKey = new Text("PRESS ANY KEY TO CONTINUE...", 250, 420, new Font("Arial", Font.BOLD, 16), Color.WHITE);
        
        pressKey.setKeyListener(new KeyListener() {
            @Override
            public void onKeyEvent(KeyEvent event) {
                if (event.getID() == KeyEvent.KEY_PRESSED)
                    new Game(board, menu).start();
            }
        });
        
        board.addObject(background);
        board.addObject(story);
        board.addObject(pressKey);
        
        board.unlock();
    }
}
