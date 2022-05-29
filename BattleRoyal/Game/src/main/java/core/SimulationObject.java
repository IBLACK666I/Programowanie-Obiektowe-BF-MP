package core;

import utils.Point;

/**
 * Abstrakcyjna klasa, która reprezentuje wszystkie obiekty znajdujące się w symulacji. Zapewnia ona interface wymagany
 * podczas funkcjonowania symulacji
 */
public abstract class SimulationObject {
    /**
     * Aktualna pozycja danego obiektu na mapie
     */
    protected Point position;

    /**
     * Znak, przez który reprezentowany jest dany obiekt
     */
    protected char sign;

    /**
     * Konstruktor tworzy obiekt symulacji
     * @param initPosition początkowa pozycja obiektu
     * @param sign znak, który reprezentuje dany obiekt
     */
    public SimulationObject(Point initPosition, char sign) {
        this.position = initPosition;
        this.sign = sign;
    }

    /**
     * Metoda ustawia aktualną pozycję obiekt na tą przekazaną jako argument
     * @param position nowa pozycja obiektu na mapie
     */
    public void setNewPosition(Point position){
        this.position = position;
    }

    /**
     * Metoda zwraca aktualna pozycję
     * @return obiekt klasy point reprezentujący aktualną pozycję obiektu
     */
    public Point getPosition(){
        return position;
    }

    /**
     * Metoda zwraca znak danego obiektu
     * @return zwraca znak, który reprezentuje dany obiekt na mapie
     */
    public char getSign(){
        return sign;
    }
}
