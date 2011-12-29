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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import lostmazed.MainMenu;
import soga2d.GraphicBoard;
import soga2d.objects.Text;
import soga2d.events.KeyListener;

/**
 * The game story screen.
 * @author Matúš Sulír
 */
public class Story {
    private static final String[] storyTexts = {
            "One sunny day you decided to make a trip and visit a small hedge maze.\n"
            + "As you are walking, you suddenly observe stairs leading under the ground. You descend them\n"
            + "and find a system of underground corridors. After half an hour, you realize that you are...\n"
            + "lost in a maze!",
            "Well, you gou out of the underground corridors. But you are in a hedge maze, don't you remember?",
            "Finally, you are free!"
    };
    
    private GraphicBoard board;
    private MainMenu menu;
    private Game game;
    
    /**
     * Constructs the story screen.
     * @param board the board to draw on
     * @param menu the game menu
     * @param game the game object
     */
    public Story(GraphicBoard board, MainMenu menu, Game game) {
        this.board = board;
        this.menu = menu;
        this.game = game;
    }
    
    /**
     * Shows the story and waits for the user to press a key.
     * @param partNumber the game part number
     */
    public void show(final int partNumber) {
        board.lock();
        board.clear();

        Text story = new Text(storyTexts[partNumber - 1], 30, 300, new Font("Arial", Font.BOLD, 16), Color.LIGHT_GRAY);
        Text pressKey = new Text("Press ENTER to continue...", 250, 420, new Font("Arial", Font.BOLD, 16), Color.WHITE);

        pressKey.setKeyListener(new KeyListener() {
            @Override
            public void onKeyEvent(KeyEvent event) {
                if (event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (partNumber == storyTexts.length)
                        menu.show();
                    else
                        game.start(partNumber);
                }
            }
        });

        board.addObjects(story, pressKey);
        board.unlock();
    }
}
