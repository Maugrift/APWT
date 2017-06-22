package boldorf.apwt;

import boldorf.apwt.glyphs.ColorString;
import boldorf.apwt.glyphs.ColorChar;
import boldorf.apwt.screens.Screen;
import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import boldorf.apwt.windows.Border;
import boldorf.apwt.windows.Line;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import squidpony.squidmath.Coord;

/**
 * A class designed to create, contain, and operate an
 * {@link asciiPanel.AsciiPanel}, as well as providing many utility functions.
 */
public class Display extends JFrame implements KeyListener
{
    /**
     * The default line width of {@link Border Borders} and {@link Line Lines}.
     */
    public static final int DEFAULT_LINES = 1;
    
    /**
     * The {@link asciiPanel.AsciiPanel} that will be used to display all
     * "graphical" data.
     */
    private AsciiPanel panel;
    
    /**
     * The current {@link boldorf.apwt.screens.Screen} being displayed by the
     * panel and where input from the KeyListener is being processed.
     */
    private Screen screen;
    
    /**
     * Creates a {@link Display} to wrap a constructed
     * {@link asciiPanel.AsciiPanel}.
     * @param panel the {@link asciiPanel.AsciiPanel} to wrap with the
     * {@link Display}
     */
    public Display(AsciiPanel panel)
    {
        super();
        this.panel = panel;
        add(this.panel);
        pack();
    }
    
    /**
     * Initializes the {@link boldorf.apwt.screens.Screen} and keylistener,
     * as well as setting up default settings outside of a constructor.
     * @param startScreen the initial {@link boldorf.apwt.screens.Screen} to
     * display
     * @return the initialized display, this
     */
    public Display init(Screen startScreen)
    {
        screen = startScreen;
        addKeyListener(this);
        repaint();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        return this;
    }
    
    @Override
    public void repaint()
    {
        panel.clear();
        screen.displayOutput();
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        screen = screen.processInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    /**
     * Returns this {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @return this {@link Display}'s {@link asciiPanel.AsciiPanel}
     */
    public AsciiPanel getPanel()
        {return panel;}
    
    /**
     * Returns this {@link Display}'s current
     * {@link boldorf.apwt.screens.Screen}.
     * @return this {@link Display}'s current
     * {@link boldorf.apwt.screens.Screen}
     */
    public Screen getScreen()
        {return screen;}
    
    /**
     * Writes a String to this {@link Display}'s {@link asciiPanel.AsciiPanel},
     * using the default
     * foreground and background colors.
     * @param s the String to write
     * @param point the point, in characters, at which the String will be
     * written
     */
    public void write(Coord point, String s)
        {panel.write(s, point.x, point.y);}
    
    /**
     * Writes a character to this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}, using the default foreground and
     * background colors.
     * @param c the char to write
     * @param point the point, in characters, at which the character will be
     * written
     */
    public void write(Coord point, char c)
        {panel.write(c, point.x, point.y);}
    
    /**
     * Writes a {@link boldorf.apwt.glyphs.ColorChar} to this
     * {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @param cc the {@link boldorf.apwt.glyphs.ColorChar} to write
     * @param point  the point, in characters, at which the
     * {@link boldorf.apwt.glyphs.ColorChar} will be
     * written
     */
    public void write(Coord point, ColorChar cc)
    {
        panel.write(cc.getChar(), point.x, point.y, cc.getForeground(),
                cc.getBackground());
    }
    
    /**
     * Writes an array of Strings to this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}, with each String
     * on the line below the previous.
     * @param s the Strings to write
     * @param point the point, in characters, at which the first String will be
     * written
     */
    public void write(Coord point, String... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(Coord.get(point.x, point.y + line)))
                write(Coord.get(point.x, point.y + line), s[line]);
    }
    
    /**
     * Writes a String to this {@link Display}'s {@link asciiPanel.AsciiPanel},
     * using the colors defined in the {@link boldorf.apwt.glyphs.ColorString}.
     * @param s the String to write
     * @param point the point, in characters, at which the String will be
     * written
     * @param colors the colors of the first
     * {@link boldorf.apwt.glyphs.ColorChar} in this
     * {@link boldorf.apwt.glyphs.ColorString} will be used on all matching
     * characters of the String printed; if a character is not found in the
     * {@link boldorf.apwt.glyphs.ColorString}, default colors will be used
     * instead
     */
    public void write(Coord point, ColorString colors, String s)
    {
        if (colors == null)
            return;
        
        for (int i = 0; i < s.length(); i++)
        {
            char cur = s.charAt(i);
            ColorChar curColors = colors.getColorChar(cur);
            if (curColors == null)
            {
                panel.write(cur, point.x + i, point.y);
            }
            else
            {
                curColors.syncDefaults(this);
                panel.write(cur, point.x + i, point.y,
                        curColors.getForeground(), curColors.getBackground());
            }
        }
    }
    
    /**
     * Writes the {@link boldorf.apwt.glyphs.ColorChar}s of a
     * {@link boldorf.apwt.glyphs.ColorString} to this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}.
     * @param s the {@link boldorf.apwt.glyphs.ColorString} to write
     * @param point the point, in characters, at which the
     * {@link boldorf.apwt.glyphs.ColorString} will be written
     */
    public void write(Coord point, ColorString s)
    {
        if (s == null || s.getSet() == null || s.getSet().isEmpty())
            return;
        
        List<ColorChar> chars = s.getSet();
        
        for (int i = 0; i < chars.size(); i++)
        {
            chars.get(i).syncDefaults(this);
            panel.write(chars.get(i).getChar(), point.x + i, point.y,
                    chars.get(i).getForeground(), chars.get(i).getBackground());
        }
    }
    
    /**
     * Writes an array of {@link boldorf.apwt.glyphs.ColorString ColorStrings} to
     * this {@link Display}'s {@link asciiPanel.AsciiPanel}, with each
     * {@link boldorf.apwt.glyphs.ColorString} on the line below the previous.
     * @param s the {@link boldorf.apwt.glyphs.ColorString ColorStrings} to write
     * @param point the point, in characters, at which the first
     * {@link boldorf.apwt.glyphs.ColorString} will
     * be written
     */
    public void write(Coord point, ColorString... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null && contains(Coord.get(point.x, point.y + line)))
                write(Coord.get(point.x, point.y + line), s[line]);
    }
    
    /**
     * Writes an array of Strings to the center of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}.
     * @param s the Strings to write
     * @param point the point at which the first String will be written
     */
    public void writeCenter(Coord point, String... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
            if (s[line] != null)
                write(Coord.get(point.x - (s[line].length() / 2),
                        point.y - (s.length / 2) + line + 1), s[line]);
    }
    
    /**
     * Writes an array of
     * {@link boldorf.apwt.glyphs.ColorString ColorStrings} to this
     * {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @param s the {@link boldorf.apwt.glyphs.ColorString ColorStrings} to
     * write
     * @param point the point at which the first
     * {@link boldorf.apwt.glyphs.ColorString ColorString} will be written
     */
    public void writeCenter(Coord point, ColorString... s)
    {
        if (s == null || s.length == 0)
            return;
        
        for (int line = 0; line < s.length; line++)
        {
            if (s[line] != null)
            {
                // Create a copy to avoid changing the original parameters
                ColorString copy = new ColorString(s[line]).syncDefaults(this);
                write(Coord.get(point.x - (copy.length() / 2),
                        point.y - (s.length / 2) + line), copy);
            }
        }
    }
    
    /**
     * Writes an array of Strings to the center of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}.
     * @param s the Strings to write
     * @param y the line on which the first String will be written
     */
    public void writeCenter(int y, String... s)
        {writeCenter(Coord.get(getCenterX(), y), s);}
    
    /**
     * Writes an array of
     * {@link boldorf.apwt.glyphs.ColorString ColorStrings} to this
     * {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @param s the {@link boldorf.apwt.glyphs.ColorString ColorStrings} to
     * write
     * @param y the line on which the first
     * {@link boldorf.apwt.glyphs.ColorString ColorString} will be written
     */
    public void writeCenter(int y, ColorString... s)
        {writeCenter(Coord.get(getCenterX(), y), s);}
    
    /**
     * Writes an array of Strings to the center of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel}.
     * @param s the Strings to write
     */
    public void writeCenter(String... s)
        {writeCenter(getCenter(), s);}
    
    /**
     * Writes an array of
     * {@link boldorf.apwt.glyphs.ColorString ColorStrings} to this
     * {@link Display}'s {@link asciiPanel.AsciiPanel}.
     * @param s the {@link boldorf.apwt.glyphs.ColorString ColorStrings} to
     * write
     */
    public void writeCenter(ColorString... s)
        {writeCenter(getCenter(), s);}
    
    /**
     * Returns true if this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * contains the given Coord.
     * @param point the Coord to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the Coord
     */
    public boolean contains(Coord point)
        {return containsX(point.x) && containsY(point.y);}
    
    /**
     * Returns true if this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * contains the given x value, in characters.
     * @param x the x value, in characters, to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the x value
     */
    public boolean containsX(int x)
        {return x >= 0 && x < panel.getWidthInCharacters();}
    
    /**
     * Returns true if this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * contains the given y value, in characters.
     * @param y the y value, in characters, to check for containment on the
     * {@link asciiPanel.AsciiPanel}
     * @return true if the {@link asciiPanel.AsciiPanel} contains the y value
     */
    public boolean containsY(int y)
        {return y >= 0 && y < panel.getHeightInCharacters();}
    
    /**
     * Returns the width of this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * in characters.
     * @return the width of this {@link Display}'s {@link asciiPanel.AsciiPanel}
     * in characters
     */
    public int getCharWidth() 
        {return panel.getWidthInCharacters();}
    
    /**
     * Returns the height of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel} in characters.
     * @return the height of this {@link Display}'s
     * {@link asciiPanel.AsciiPanel} in characters
     */
    public int getCharHeight()
        {return panel.getHeightInCharacters();}
    
    /**
     * Returns the x coordinate at the center of the {@link Display}.
     * @return the x coordinate at the center of the {@link Display}
     */
    public int getCenterX()
        {return getCharWidth() / 2;}
    
    /**
     * Returns the y coordinate at the center of the {@link Display}.
     * @return the y coordinate at the center of the {@link Display}
     */
    public int getCenterY()
        {return getCharHeight() / 2;}
    
    /**
     * Returns the coordinates at the center of the {@link Display}.
     * @return the coordinates at the center of the {@link Display}
     */
    public Coord getCenter()
        {return Coord.get(getCenterX(), getCenterY());}
    
    /**
     * Draws a {@link Line} between two endpoints to the provided Display.
     * @param end1 the first endpoint; must be a different point than the second
     * endpoint, have one axis value in common, and be on the display
     * @param end2 the second endpoint; must be a different point than the first
     * endpoint, have one axis value in common, and be on the display
     * @param border the characters of the {@link Line}; if horizontal, points
     * must share y values, and the opposite is true with x values
     * @return true if the {@link Line} was successfully drawn
     */
    public boolean drawLine(Coord end1, Coord end2,
            Line border)
    {
        if (end1.equals(end2))
            throw new IllegalArgumentException("Both endpoints may not be the "
                    + "same point");
        
        if (end1.x != end2.x && end1.y != end2.y)
            throw new IllegalArgumentException("Both endpoints must share an "
                    + "axis value");
        
        if (!contains(end1) || !contains(end2))
            throw new IndexOutOfBoundsException("The display must contain both "
                    + "endpoints");
        
        if ((end1.x == end2.x && border.horizontal) ||
                (end1.y == end2.y && !border.horizontal))
            throw new IllegalArgumentException("Endpoint dimension does not "
                    + "match line horizontal/vertical field");
        
        border.syncDefaults(this);
        
        write(end1, new ColorChar(border.end1,
                border.getForeground(), border.getBackground()));
        write(end2, new ColorChar(border.end2,
                border.getForeground(), border.getBackground()));
        
        int start, end;
        
        if (!border.horizontal)
        {
            if (end1.y < end2.y)
            {
                start = end1.y;
                end = end2.y;
            }
            else
            {
                start = end2.y;
                end = end1.y;
            }
            
            for (int i = start + 1; i < end; i++)
                write(Coord.get(end1.x, i), new ColorChar(border.line,
                        border.getForeground(), border.getBackground()));
        }
        else
        {
            if (end1.x < end2.x)
            {
                start = end1.x;
                end = end2.x;
            }
            else
            {
                start = end2.x;
                end = end1.x;
            }
            
            for (int i = start + 1; i < end; i++)
                write(Coord.get(i, end1.y), new ColorChar(border.line,
                        border.getForeground(), border.getBackground()));
        }
        
        return true;
    }
    
    /**
     * Draws a {@link Border}  between two specified corners to the provided
     * {@link boldorf.apwt.Display}, filled with the provided fill color.
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param border the characters of the {@link Border}
     * @param fill the Color to fill the center of the {@link Border} with; if
     * null, no fill will be performed
     * @return true if the {@link Border} was successfully drawn
     */
    public boolean drawBorder(Coord corner1,
            Coord corner2, Border border, Color fill)
    {
        if (corner1.x == corner2.x || corner1.y == corner2.y)
            throw new IllegalArgumentException("Corners must have different "
                    + "axis values");
        
        if (!contains(corner1) || !contains(corner2))
            throw new IllegalArgumentException("The display must contain both "
                    + "corners");
        
        Coord tl, tr, bl, br;
        if (corner1.y < corner2.y) // corner1 is above corner2
        {
            if (corner1.x < corner2.x) // corner1 is left of corner2
            {
                tl = corner1;
                tr = Coord.get(corner2.x, corner1.y);
                bl = Coord.get(corner1.x, corner2.y);
                br = corner2;
            }
            else // corner1 is right of corner2
            {
                tl = Coord.get(corner2.x, corner1.y);
                tr = corner1;
                bl = corner2;
                br = Coord.get(corner1.x, corner2.y);
            }
        }
        else // corner1 is below corner2
        {
            if (corner1.x < corner2.x) // corner1 is left of corner2
            {
                tl = Coord.get(corner1.x, corner2.y);
                tr = corner2;
                bl = corner1;
                br = Coord.get(corner2.x, corner1.y);
            }
            else // corner1 is right of corner2
            {
                tl = corner2;
                tr = Coord.get(corner1.x, corner2.y);
                bl = Coord.get(corner2.x, corner1.y);
                br = corner1;
            }
        }
        
        border.syncDefaults(this);
        
        write(tl, new ColorChar(border.cornerTL,
                border.getForeground(), border.getBackground()));
        write(tr, new ColorChar(border.cornerTR,
                border.getForeground(), border.getBackground()));
        write(bl, new ColorChar(border.cornerBL,
                border.getForeground(), border.getBackground()));
        write(br, new ColorChar(border.cornerBR,
                border.getForeground(), border.getBackground()));
        
        for (int x = tl.x + 1; x < tr.x; x++)
        {
            getPanel().write(border.edgeT, x, tl.y,
                    border.getForeground(), border.getBackground());
            getPanel().write(border.edgeB, x, bl.y,
                    border.getForeground(), border.getBackground());
        }
        
        for (int y = tl.y + 1; y < bl.y; y++)
        {
            getPanel().write(border.edgeL, tl.x, y,
                    border.getForeground(), border.getBackground());
            getPanel().write(border.edgeR, tr.x, y,
                    border.getForeground(), border.getBackground());
        }
        
        if (fill == null)
            return true;
        
        for (int y = tl.y + 1; y < bl.y; y++)
            for (int x = tl.x + 1; x < tr.x; x++)
                write(Coord.get(x, y), new ColorChar(ExtChars.BLOCK, fill));
        
        return true;
    }
    
    /**
     * Draws a {@link Border}  of the specified width between two specified
     * corners.
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param width the width of the {@link Border} to draw; must be 1 or 2
     * @param fill the Color to fill the center of the {@link Border} with; if
     * null, no fill will be performed
     * @return true if the {@link Border} was successfully drawn
     */
    public boolean drawBorder(Coord corner1, Coord corner2, int width,
            Color fill)
        {return drawBorder(corner1, corner2, new Border(width), fill);}
    
    /**
     * Draws a {@link Border} between two specified corners and filled with the
     * background color of the provided {@link Border}.
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param border the characters of the {@link Border}
     * @return true if the {@link Border} was successfully drawn
     */
    public boolean drawBorder(Coord corner1, Coord corner2, Border border)
    {
        border.syncDefaults(this);
        return drawBorder(corner1, corner2, border, border.getBackground());
    }
    
    /**
     * Draws a {@link Border} of the specified width between two specified
     * corners. No fill will be performed.
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @param width the width of the {@link Border} to draw; must be 1 or 2
     * @return true if the {@link Border} was successfully drawn
     */
    public boolean drawBorder(Coord corner1, Coord corner2, int width)
        {return drawBorder(corner1, corner2, width, null);}
    
    /**
     * Draws a {@link Border} of the default width between two specified
     * corners. No fill will be performed.
     * @param corner1 the first corner; must be a different point than the
     * second corner, share no axis values, and be on the display
     * @param corner2 the second corner; must be a different point than the
     * first corner, share no axis values, and be on the display
     * @return true if the {@link Border} was successfully drawn
     */
    public boolean drawBorder(Coord corner1, Coord corner2)
        {return drawBorder(corner1, corner2, DEFAULT_LINES);}
}