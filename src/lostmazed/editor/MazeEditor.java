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
package lostmazed.editor;

import lostmazed.game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFileChooser;
import lostmazed.*;
import lostmazed.game.Maze;
import lostmazed.game.Player;
import soga2d.GraphicBoard;
import soga2d.events.KeyListener;
import soga2d.events.MouseClickListener;
import soga2d.objects.*;

/**
 * The maze editor allows the user to interactively create a maze from a
 * background and an image representing the maze itself. The user also
 * specifies a starting and ending point.
 * @author Matúš Sulír
 */
public class MazeEditor {
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 12);
    private static final Color BUTTON_COLOR = Color.WHITE;
    private static final Color BUTTON_FILL = new Color(0, 0, 0, 100);
    private static final Font MARKER_FONT = new Font("Arial", Font.BOLD, 9);
    
    private GraphicBoard board;
    private MainMenu menu;
    private Rectangle loadBackground;
    private Rectangle loadMaze;
    private Rectangle test;
    private Rectangle exit;
    private Texture background = new Texture(Game.WIDTH, Game.HEIGHT);
    private Picture mazePicture = new Picture();
    private Rectangle start;
    private Rectangle end;
    
    /**
     * Constructs a maze editor.
     * @param board the board to draw on
     */
    public MazeEditor(GraphicBoard board, MainMenu menu) {
        this.board = board;
        this.menu = menu;
        
        loadBackground = createButton("Load background", 20, new MouseClickListener() {
            @Override
            public void onClick() {
                loadBackground();
            }
        });
        
        loadMaze = createButton("Load maze", 60, new MouseClickListener() {
            @Override
            public void onClick() {
                loadMaze();
            }
        });
        
        test = createButton("Test", 100, new MouseClickListener() {
            @Override
            public void onClick() {
                test();
            }
        });
        
        exit = createButton("Exit editor", 140, new MouseClickListener() {
            @Override
            public void onClick() {
                exit();
            }
        });
        
        start = new Rectangle(Game.WIDTH / 2 - 50, Game.HEIGHT / 2, 20, 20, Color.GREEN, new Color(0, 255, 0, 100));
        start.addSubobject(new Text("start", 0, 0, MARKER_FONT, Color.WHITE));
        start.enableDragDrop();
        
        end = new Rectangle(Game.WIDTH / 2 + 50, Game.HEIGHT / 2, 20, 20, Color.RED, new Color(255, 0, 0, 100));
        end.addSubobject(new Text("end", 0, 0, MARKER_FONT, Color.WHITE));
        end.enableDragDrop();
    }
    
    /**
     * Shows the maze editor.
     */
    public void show() {
        board.lock();
        board.clear();
        board.addObjects(background, mazePicture, loadBackground, loadMaze, test, exit, start, end);

        board.setKeyPressListener(new KeyListener() {
            @Override
            public void onKeyEvent(KeyEvent event) {
                if (event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == KeyEvent.VK_ESCAPE)
                    exit();
            }
        });
        
        board.unlock();
    }
    
    /**
     * Creates a clickable button.
     * @param label the button label
     * @param y the y coordinate
     * @param clickAction the action to perform after the user clicks the button
     * @return the rectangle representing the button
     */
    private Rectangle createButton(String label, int y, MouseClickListener clickAction) {
        Rectangle button = new Rectangle(Game.WIDTH - 120, y, 110, 30, BUTTON_COLOR, BUTTON_FILL);
        
        button.addSubobject(new Text(label, 5, 5, BUTTON_FONT, BUTTON_COLOR));
        button.setMouseClickListener(clickAction);
        
        return button;
    }
    
    /**
     * Displays a dialog to open a file and loads the background image.
     */
    private void loadBackground() {
        ImageDialog dialog = new ImageDialog();
        
        if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                background.loadFromFile(dialog.getSelectedFile());
            } catch (IOException ex) {
                new MessageDialog(board, "The background image could not be loaded.").openOK();
            }
        }
        
    }
    
    /**
     * Displays a dialog to open a file and loads the maze image.
     */
    private void loadMaze() {
        ImageDialog dialog = new ImageDialog();
        
        if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                mazePicture.loadFromFile(dialog.getSelectedFile());
            } catch (IOException ex) {
                new MessageDialog(board, "The maze image could not be loaded.").openOK();
            }
        }
    }
    
    /**
     * Tests a newly created maze.
     */
    private void test() {
        board.clear();
        Maze maze = new Maze(board, background, mazePicture);
        board.addObject(start);
        board.addObject(end);
        
        try {
            Player player = new Player(board, maze);
            maze.startPlaying(player, start.getRectangle().getLocation(), new Point());
            
            board.setKeyPressListener(new KeyListener() {
                @Override
                public void onKeyEvent(KeyEvent event) {
                    if (event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == KeyEvent.VK_ESCAPE)
                        show();
                }
            });
        } catch (IOException ex) {
            new MessageDialog(board, "The player animation could not be loaded.").openOK();
        }
    }
    
    /**
     * Displays a confirmation dialog and quits the editor if the user
     * chooses "Yes".
     */
    private void exit() {
        new MessageDialog(board, "Do you really want to quit the editor?").openYesNo(new Runnable() {
            @Override
            public void run() {
                menu.show();
            }
        }, null);
    }
}
