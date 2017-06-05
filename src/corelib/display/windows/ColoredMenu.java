package corelib.display.windows;

import java.awt.Color;

/**
 * A Menu that uses color to show which item is currently selected.
 * @param <WindowType> the type of {@link Window} used by the {@link Menu}
 */
public abstract class ColoredMenu<WindowType extends Window>
        extends Menu<WindowType>
{
    /** The foreground color that the current selection will be set to. */
    private Color selectionForeground;
    
    /** The background color that the current selection will be set to. */
    private Color selectionBackground;
    
    /**
     * Creates a {@link ColoredMenu} from a {@link Window}, initial selection,
     * and selection colors.
     * @param window the {@link Menu}'s {@link Window}
     * @param initialSelection the item that the {@link Menu} will have selected
     * initially
     * @param foreground the foreground color of selected items
     * @param background the background color of selected items
     */
    public ColoredMenu(WindowType window, int initialSelection,
            Color foreground, Color background)
    {
        super(window, initialSelection);
        selectionForeground = foreground;
        selectionBackground = background;
    }
    
    /**
     * Creates a {@link Menu} from a {@link Window} and selection colors,
     * starting with the first item selected.
     * @param window the {@link Menu}'s {@link Window}
     * @param foreground the foreground color of selected items
     * @param background the background color of selected items
     */
    public ColoredMenu(WindowType window, Color foreground, Color background)
        {this(window, 0, foreground, background);}
    
    /**
     * Returns the foreground color of selected items.
     * @return the foreground color of selected items
     */
    public Color getForeground()
        {return selectionForeground;}
    
    /**
     * Returns the background color of selected items.
     * @return the background color of selected items
     */
    public Color getBackground()
        {return selectionBackground;}
}