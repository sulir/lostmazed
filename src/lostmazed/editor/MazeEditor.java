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
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import lostmazed.*;
import lostmazed.game.Maze;
import lostmazed.game.Player;
import soga2d.GraphicBoard;
import soga2d.GraphicObject;
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
    private Maze maze;
    private GraphicObject newMaze, load, setBackground, setMaze, test, save, exit;
    private Rectangle start;
    private Rectangle end;
    private ImageDialog imageDialog = new ImageDialog();
    private MazeDialog mazeDialog = new MazeDialog();
    
    /**
     * Constructs a maze editor.
     * @param board the board to draw on
     * @param menu the main menu
     */
    public MazeEditor(GraphicBoard board, MainMenu menu) {
        this.board = board;
        this.menu = menu;
        
        newMaze = createButton("New", 110, 10, 45, new MouseClickListener() {
            @Override
            public void onClick() {
                newMaze();
            }
        });
        
        load = createButton("Load", 55, 10, 45, new MouseClickListener() {
            @Override
            public void onClick() {
                load();
            }
        });
        
        setBackground = createButton("Set background", 110, 45, 100, new MouseClickListener() {
            @Override
            public void onClick() {
                setBackground();
            }
        });
        
        setMaze = createButton("Set maze walls", 110, 80, 100, new MouseClickListener() {
            @Override
            public void onClick() {
                setMaze();
            }
        });
        
        test = createButton("Test", 110, 115, 100, new MouseClickListener() {
            @Override
            public void onClick() {
                test();
            }
        });
        
        save = createButton("Save", 110, 150, 100, new MouseClickListener() {
            @Override
            public void onClick() {
                save();
            }
        });
        
        exit = createButton("Exit editor", 110, 185, 100, new MouseClickListener() {
            @Override
            public void onClick() {
                exit();
            }
        });
        
        start = new Rectangle(Game.WIDTH / 2 - 50, Game.HEIGHT / 2, Maze.END_SIZE, Maze.END_SIZE,
                Color.GREEN, new Color(0, 255, 0, 100));
        start.addSubobject(new Text("start", 0, 0, MARKER_FONT, Color.WHITE));
        start.enableDragDrop();
        
        end = new Rectangle(Game.WIDTH / 2 + 50, Game.HEIGHT / 2, Maze.END_SIZE, Maze.END_SIZE,
                Color.RED, new Color(255, 0, 0, 100));
        end.addSubobject(new Text("end", 0, 0, MARKER_FONT, Color.WHITE));
        end.enableDragDrop();
        
        maze = new Maze(new Texture(Game.WIDTH, Game.HEIGHT), new Picture(),
                start.getRectangle().getLocation(), end.getRectangle().getLocation());
    }
    
    /**
     * Shows the maze editor.
     */
    public void show() {
        board.lock();
        board.clear();

        start.moveTo((int) maze.getStart().getX(), (int) maze.getStart().getY());
        end.moveTo((int) maze.getEnd().getX(), (int) maze.getEnd().getY());
        
        maze.addToBoard(board);
        board.addObjects(newMaze, load, setBackground, setMaze, test, save, exit, start, end);

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
     * @param xDistance the x-coordinate distance from the right border of the board
     * @param y the y coordinate
     * @param width the button width
     * @param clickAction the action to perform after the user clicks the button
     * @return the rectangle representing the button
     */
    private GraphicObject createButton(String label, int xDistance, int y, int width, MouseClickListener clickAction) {
        Rectangle button = new Rectangle(Game.WIDTH - xDistance, y, width, 25, BUTTON_COLOR, BUTTON_FILL);
        
        button.addSubobject(new Text(label, 5, 2, BUTTON_FONT, BUTTON_COLOR));
        button.setMouseClickListener(clickAction);
        
        return button;
    }
    
    /**
     * Creates a new, plain maze.
     */
    private void newMaze() {
        new MazeEditor(board, menu).show();
    }
    
    /**
     * Loads the maze from a file.
     */
    private void load() {
        if (mazeDialog.showOpenDialog(null) == ImageDialog.APPROVE_OPTION) {
            try {
                maze = Maze.load(mazeDialog.getSelectedFile());
                show();
            } catch (IOException ex) {
                new MessageDialog(board, "The maze could not be loaded.").openOK();
            }
        }
    }
    
    /**
     * Displays a dialog to open a file and loads the background image.
     */
    private void setBackground() {
        if (imageDialog.showOpenDialog(null) == ImageDialog.APPROVE_OPTION) {
            try {
                maze.setBackground(new Texture(imageDialog.getSelectedFile(), Game.WIDTH, Game.HEIGHT));
                show();
            } catch (IOException ex) {
                new MessageDialog(board, "The background image could not be loaded.").openOK();
            }
        }
        
    }
    
    /**
     * Displays a dialog to open a file and loads the maze image.
     */
    private void setMaze() {
        if (imageDialog.showOpenDialog(null) == ImageDialog.APPROVE_OPTION) {
            try {
                maze.setMaze(new Picture(imageDialog.getSelectedFile()));
                show();
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
        maze.setEndpoints(start.getRectangle().getLocation(), end.getRectangle().getLocation());
        
        try {
            Player player = new Player(board, maze);
            maze.startPlaying(board, player, new Runnable() {
                @Override
                public void run() {
                    new MessageDialog(board, "The maze was finished successfully.").openOK(new Runnable() {
                        @Override
                        public void run() {
                            show();
                        }
                    });
                }
            });
            
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
     * Saves the maze into a file.
     */
    private void save() {
        if (mazeDialog.showSaveDialog(null) == MazeDialog.APPROVE_OPTION) {
            maze.setEndpoints(start.getRectangle().getLocation(), end.getRectangle().getLocation());
            
            try {
                File file = mazeDialog.getSelectedFile();
                
                if (file.getName().toLowerCase().endsWith(".maze"))
                    maze.save(file);
                else
                    maze.save(new File(file + ".maze"));
            } catch (IOException ex) {
                new MessageDialog(board, "The maze could not be saved.").openOK();
            }
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
