package corelib.display.screens;

import corelib.display.glyphs.ColorSet;
import corelib.display.glyphs.ColorString;
import corelib.display.windows.PopupWindow;
import java.awt.Color;

/**
 * A {@link Terminal} displayed through a
 * {@link corelib.display.windows.PopupWindow}.
 */
public class PopupTerminal extends ColoredTerminal<PopupWindow>
{
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     * @param foreground the color of the input characters
     * @param background the color of the input background
     */
    public PopupTerminal(PopupWindow output, ColorSet prompt, int length,
            Color foreground, Color background)
        {super(output, prompt, length, foreground, background);}
    
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     * @param foreground the color of the input characters
     */
    public PopupTerminal(PopupWindow output, ColorSet prompt, int length,
            Color foreground)
        {super(output, prompt, length, foreground);}
    
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     */
    public PopupTerminal(PopupWindow output, ColorSet prompt, int length)
        {super(output, prompt, length);}
    
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window},
     * prompt, and maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders} and the
     * prompt
     */
    public PopupTerminal(PopupWindow output, String prompt, int length)
        {super(output, prompt, length);}
    
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window} and maximum length,
     * but no prompt.
     * @param output the {@link Terminal}'s output window
     * @param length the {@link Terminal}'s maximum length, counting Window
     * {@link corelib.display.windows.Border borders}
     */
    public PopupTerminal(PopupWindow output, int length)
        {super(output, length);}
    
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public PopupTerminal(PopupWindow output, ColorSet prompt)
        {super(output, prompt);}
    
    /**
     * Creates an {@link PopupTerminal} with the given
     * {@link corelib.display.windows.PopupWindow Window} and
     * prompt, using the width of the
     * {@link corelib.display.Display} as the maximum length.
     * @param output the {@link Terminal}'s output window
     * @param prompt the {@link Terminal}'s prompt
     */
    public PopupTerminal(PopupWindow output, String prompt)
        {super(output, prompt);}
    
    /**
     * Creates an {@link PopupTerminal} with no prompt
     * and the width of the {@link corelib.display.Display} as the maximum
     * length.
     * @param output the {@link Terminal}'s output window
     */
    public PopupTerminal(PopupWindow output)
        {super(output);}
    
    @Override
    public void displayOutput()
    {
        getWindow().set(getWindow().getContents().size() - 1,
                new ColorSet(getPrompt()).add(new ColorString(getInput(),
                        getInputForeground(), getInputBackground())));
        getWindow().display();
    }
}