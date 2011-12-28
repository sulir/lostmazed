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

import lostmazed.game.Story;
import lostmazed.editor.MazeEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;
import lostmazed.editor.ImageDialog;
import lostmazed.editor.MazeDialog;
import lostmazed.game.Maze;
import lostmazed.game.Player;
import soga2d.GraphicBoard;
import soga2d.events.KeyListener;
import soga2d.objects.Text;
import soga2d.events.MouseClickListener;

/**
 * The main menu of the game.
 * @author Matúš Sulír
 */
public class MainMenu {
    private GraphicBoard board;
    private Text storyMode;
    private Text mazeEditor;
    private Text loadMaze;
    private Text exit;
    
    /**
     * Constructs the menu.
     * @param board the board to display the menu on
     */
    public MainMenu(GraphicBoard board) {
        this.board = board;
        
        Font font = new Font("Arial", Font.BOLD, 20);
        Color color = Color.WHITE;
        
        storyMode = new Text("STORY MODE", 400, 200, font, color);
        storyMode.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                storyMode();
            }
        });
        
        mazeEditor = new Text("MAZE EDITOR", 400, 250, font, color);
        mazeEditor.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                mazeEditor();
            }
        });
        
        loadMaze = new Text("LOAD MAZE", 400, 300, font, color);
        loadMaze.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                loadMaze();
            }
        });
        
        exit = new Text("EXIT", 400, 350, font, color);
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
        
        board.addObjects(storyMode, mazeEditor, loadMaze, exit);
        
        board.unlock();
        
        board.setKeyPressListener(new KeyListener() {
            @Override
            public void onKeyEvent(KeyEvent event) {
                if (event.getID() == KeyEvent.KEY_PRESSED && event.getKeyCode() == KeyEvent.VK_ESCAPE)
                    exit();
            }
        });
    }
    
    /**
     * Starts a new game in the story mode.
     */
    private void storyMode() {
        new Story(board, this).show();
    }
    
    /**
     * Starts the maze editor.
     */
    private void mazeEditor() {
        new MazeEditor(board, this).show();
    }
    
    /**
     * Loads a maze previously saved in the editor.
     */
    private void loadMaze() {
        MazeDialog dialog = new MazeDialog();
        
        if (dialog.showOpenDialog(null) == ImageDialog.APPROVE_OPTION) {
            try {
                Maze maze = Maze.load(dialog.getSelectedFile());
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
            } catch (IOException ex) {
                new MessageDialog(board, "The maze could not be loaded.").openOK();
            }
        }
    }
    
    /**
     * Displays a confirmation message and exists the program if the user chooses "Yes".
     */
    private void exit() {
        new MessageDialog(board, "Do you really want to quit the program?").openYesNo(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, null);
    }
}
