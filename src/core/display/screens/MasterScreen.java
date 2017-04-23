package core.display.screens;

import core.display.Border;
import core.display.ColorSet;
import core.display.ColorString;
import java.awt.event.KeyEvent;
import core.display.Display;
import core.display.LineBorder;
import core.display.PopupWindow;
import core.display.Window;
import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 */
public class MasterScreen extends Screen
{
    private int    score;
    private Window output;
    private Screen subscreen;
    
    public MasterScreen(Display d)
    {
        super(d);
        score     = 0;
        subscreen = null;
        output = new core.display.Window(display, 10, display.getCharHeight() / 4,
                new Border(2), new ArrayList<>());
        
        output.add("Earn points with Enter.");
        output.add("Press Ctrl+T to toggle the terminal.");
        output.add("Press Escape to quit.");
    }
    
    @Override
    public void displayOutput()
    {
        if (output != null)
            output.displayOutput();
        
        if (subscreen != null)
            subscreen.displayOutput();
    }

    @Override
    public Screen processInput(KeyEvent key)
    {
        if (subscreen != null)
        {
            subscreen = subscreen.processInput(key);
            return this;
        }
        
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ENTER:
                if (key.isShiftDown() && score > 0)
                {
                    score = (int) Math.pow(score, 2);
                    output.addSeparator(new LineBorder(true, 2, 1));
                    output.setSeparator(0, new LineBorder(false, 2, 1, 1));
                    output.add(new ColorString("The score has been hacked!",
                            Color.RED));
                }
                else
                {
                    score++;
                }
                
                ColorSet scoreOutput = ColorSet.toColorSet(new ColorString[]
                    {new ColorString("Your Score: "),
                    new ColorString(Integer.toString(score), Color.YELLOW)});
                
                if (output.getContents().size() >= 4)
                {
                    output.set(4, scoreOutput);
                }
                else
                {
                    output.addSeparator(new LineBorder(false, 2, 1));
                    output.add(scoreOutput);
                }
                break;
            case KeyEvent.VK_UP:
                output.setY(output.getY() - 1);
                break;
            case KeyEvent.VK_DOWN:
                output.setY(output.getY() + 1);
                break;
            case KeyEvent.VK_LEFT:
                output.setX(output.getX() - 1);
                break;
            case KeyEvent.VK_RIGHT:
                output.setX(output.getX() + 1);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_T:
                if (key.isControlDown())
                {
                    if (subscreen == null)
                    {
                        subscreen = new Terminal(new PopupWindow(display,
                                3 * (display.getCharHeight() / 4),
                                new Border(1, Color.RED, Color.BLUE)),
                                "Your Input: ", display.getCharWidth());
                    }
                    else
                    {
                        subscreen = null;
                    }
                    
                    break;
                }
                // Skip case if control was not pressed
        }
        
        return this;
    }
}
