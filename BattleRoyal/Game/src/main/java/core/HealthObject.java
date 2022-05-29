package core;

import utils.Point;

/**
 * Klasa reprezentuje punkt życia w symulacji. Jego zdobycie dodaje ilość życia
 */
public class HealthObject extends SimulationObject{

    /**
     * Konstruktor tworzy punkt życia
     * @param initPosition początkowa pozycja punktu życia
     */
    public HealthObject(Point initPosition) {
        super(initPosition, Map.HEALTH_POINT);
    }
}
