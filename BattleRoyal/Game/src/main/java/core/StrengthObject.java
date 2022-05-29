package core;

import utils.Point;

/**
 * Klasa reprezentuje punkt siły. Jego zdobycie zwiększa siłę
 */
public class StrengthObject extends SimulationObject{

    /**
     * Konstruktor odpowiednio tworzy obiekt
     * @param initPosition początkowa pozycja punktu życia
     */
    public StrengthObject(Point initPosition) {
        super(initPosition, Map.STRENGTH_POINT);
    }
}
