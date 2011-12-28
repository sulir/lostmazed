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

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import lostmazed.MainMenu;
import lostmazed.MessageDialog;
import soga2d.GraphicBoard;
import soga2d.GraphicObject;
import soga2d.events.KeyListener;
import soga2d.objects.Picture;
import soga2d.objects.Texture;

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
    private MainMenu menu;
    
    /**
     * The game constructor.
     * @param board the game graphic board
     * @param menu the game menu
     */
    public Game(GraphicBoard board, MainMenu menu) {
        this.board = board;
        this.menu = menu;
    }
    
    /**
     * Loads the game graphics and starts the game.
     */
    public void start() {
        try {
            GraphicObject background = new Texture("lostmazed/img/maze_bg.png", Game.WIDTH, Game.HEIGHT);
            GraphicObject mazePicture = new Picture("lostmazed/img/maze.png");
            
            maze = new Maze(background, mazePicture, new Point(Game.WIDTH / 2, Game.HEIGHT / 2), new Point());
            player = new Player(board, maze);
            
            maze.startPlaying(board, player, null);
            
            board.setKeyPressListener(new KeyListener() {
                @Override
                public void onKeyEvent(KeyEvent event) {
                    if (event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == KeyEvent.VK_ESCAPE)
                        exit();
                }
            });
        } catch (IOException ex) {
            new MessageDialog(board, "Could not load the game graphics.").openOK(new Runnable() {
                @Override
                public void run() {
                    menu.show();
                }
            });
        }
    }
    
    /**
     * Shows a confirmation dialog and quits the game.
     */
    public void exit() {
        new MessageDialog(board, "Do you really want to quit the game?").openYesNo(new Runnable() {
            @Override
            public void run() {
                menu.show();
            }
        }, null);
    }
}
