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

import java.io.IOException;
import lostmazed.Game;
import soga2d.GraphicBoard;
import soga2d.GraphicObject;
import soga2d.objects.Picture;
import soga2d.objects.Texture;

/**
 * The maze background and "walls".
 * @author Matúš Sulír
 */
public class Maze {
    private Texture background;
    private Picture maze;
    
    /**
     * Loads the graphics and adds the maze to the board.
     * @param board the board to add the maze to
     * @throws IOException when the image can not be loaded
     */
    public Maze(GraphicBoard board) throws IOException {
        background = new Texture("lostmazed/img/maze_bg.png", Game.WIDTH, Game.HEIGHT);
        maze = new Picture("lostmazed/img/maze.png");
        
        board.addObject(background);
        board.addObject(maze);
    }
    
    public GraphicObject getMaze() {
        return maze;
    }
}
