package corelib.map;

import corelib.display.glyphs.ColorChar;
import corelib.items.Container;
import squidpony.squidmath.Coord;

/** An expanded entity integrated with the item system. */
public class Being extends Entity
{
    // Replace with a BodyPart[] eventually
    private Container[] inventories;
    
    public Being(String name, TileMap map, Coord location, ColorChar glyph,
            Container[] inventories)
    {
        super(name, map, location, glyph);
        this.inventories = inventories;
    }
}