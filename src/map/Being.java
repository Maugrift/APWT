package map;

import core.display.ColorChar;
import items.Container;
import squidpony.squidmath.Coord;

/** An expanded entity integrated with the item system. */
public class Being extends Entity
{
    // Replace with a BodyPart[] eventually
    private Container[] inventories;
    
    public Being(String name, Map map, Coord location, ColorChar glyph,
            Container[] inventories)
    {
        super(name, map, location, glyph);
        this.inventories = inventories;
    }
}