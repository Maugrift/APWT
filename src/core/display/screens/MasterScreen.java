package core.display.screens;

import java.awt.event.KeyEvent;
import core.display.Display;

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
        
        output = new Window(display, 0, display.getCharHeight() / 2, true,
                true);
        output.getContents().addAll(java.util.Arrays.asList(new String[]
           {"Earn points with Enter.", "Press Ctrl+T to toggle the terminal.",
            "Press Escape to quit."}));
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
                if (key.isShiftDown())
                    score = (int) Math.pow(score, 2);
                else
                    score++;
                
                if (output.getContents().size() >= 4)
                    output.getContents().set(3, "Your Score: " + score);
                else
                    output.getContents().add("Your Score: " + score);
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
            case KeyEvent.VK_T:
                if (key.isControlDown())
                {
                    if (subscreen == null)
                    {
                        subscreen = new Terminal(new Window(display, 0,
                                3 * (display.getCharHeight() / 4), true, true),
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
