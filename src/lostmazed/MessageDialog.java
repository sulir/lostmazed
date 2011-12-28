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
import soga2d.events.MouseClickListener;
import soga2d.objects.Rectangle;
import soga2d.objects.Text;

/**
 * The message dialog is used to inform the user about an event or to get
 * a confirmation of some action.
 * 
 * After the user clicks any of the dialog buttons, the action may be performed
 * and the dialog is closed.
 * @author Matúš Sulír
 */
public class MessageDialog {
    private GraphicBoard board;
    private Rectangle window;
    private Rectangle okButton;
    private Rectangle yesButton;
    private Rectangle noButton;
    
    /**
     * Constructs a message dialog.
     * @param board the board which will be later used to display the dialog
     * @param message the textual message to show
     */
    public MessageDialog(GraphicBoard board, String message) {
        this.board = board;
        
        window = new Rectangle((Game.WIDTH - 400) / 2, (Game.HEIGHT - 200) / 2, 400, 200, Color.WHITE, new Color(0, 0, 0, 190));
        Text messageText = new Text(message, 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE);
        messageText.moveTo((window.getWidth() - messageText.getWidth()) / 2, 30);
        window.addSubobject(messageText);
    }
    
    /**
     * Shows the dialog with one button named "OK".
     */
    public void openOK() {
        openOK(null);
    }
    
    /**
     * Shows the dialog with one button named "OK".
     * 
     * The specified action is performed after the user clicks the button.
     */
    public void openOK(Runnable okAction) {
        okButton = createButton("OK", 160, okAction);
        
        board.addObjects(window, okButton);
    }
    
    /**
     * Shows the dialog with two buttons - "Yes" and "No".
     * 
     * The specified action is performed after the user clicks the button. Any
     * of the actions can be null - in that case no specific action is
     * performed.
     */
    public void openYesNo(Runnable yesAction, Runnable noAction) {
        yesButton = createButton("Yes", 100, yesAction);
        noButton = createButton("No", 220, noAction);
        
        board.addObjects(window, yesButton, noButton);
    }
    
    /**
     * Closes the dialog immediately.
     */
    public void close() {
        board.removeObjects(window, okButton, yesButton, noButton);
    }
    
    /**
     * Creates a dialog button.
     * @param label the label on the button
     * @param x the x coordinate
     * @param action the action to perform (can be null)
     * @return the created button as a Rectangle object
     */
    private Rectangle createButton(String label, int x, final Runnable action) {
        Rectangle button = new Rectangle(window.getX() + x, window.getY() + 140, 80, 30, Color.WHITE, Color.WHITE);
        
        Text okText = new Text(label, 25, 0, new Font("Arial", Font.BOLD, 17), Color.BLACK);
        button.addSubobject(okText);
        
        button.setMouseClickListener(new MouseClickListener() {
            @Override
            public void onClick() {
                if (action != null)
                    action.run();
                
                close();
            }
        });
        
        return button;
    }
}
