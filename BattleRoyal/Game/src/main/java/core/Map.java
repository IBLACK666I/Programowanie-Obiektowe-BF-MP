package core;

import utils.Point;

import java.util.Random;

/**
 * Klasa reprezentuje mapę w symulacji
 */
public class Map {

    /**
     * Obiekt, który generuje losowe liczby
     */
    private final Random random;

    /**
     * Tablica dwuwymiarowa, która przechowuje znaki wszystkich obiektów
     */
    private final char[][]points;

    /**
     * Szerokość mapy
     */
    private final int width;

    /**
     * Wysokość mapy
     */
    private final int height;

    /**
     * Znak reprezentuje punkt życia
     */
    public static final char HEALTH_POINT = '+';

    /**
     * Znak reprezentuje punkt siły
     */
    public static final char STRENGTH_POINT = '#';

    /**
     * Znak reprezentuje puste pole
     */
    public static final char EMPTY = ' ';

    /**
     * Znak reprezentuje granice mapy
     */
    public static final char BORDER = '*';

    /**
     * Konstruktor reprezentuje mapę w symulacji
     * @param width szerokość mapy, która zostanie utworzona
     * @param height wysokość mapy, która zostanie utworzona
     * @throws IllegalArgumentException wyjątek zostanie wyrzucony, gdy wysykość badź szerokość mapy będzie mniejsza niż 5
     */
    public Map(int width, int height) throws IllegalArgumentException{
        if(width < 5) throw new IllegalArgumentException("The width for the map cannot be less than 5");
        if(height < 5) throw new IllegalArgumentException("The height for the map cannot be less than 5");

        this.random = new Random();
        this.points = new char[height][width];
        this.width = width;
        this.height = height;
        for(int h = 0; h < height; ++h){
            for(int w = 0; w < width; ++w){
                if(h == 0 || h == height-1) points[h][w] = Map.BORDER;
                else if(w == 0 || w == width-1) points[h][w] = Map.BORDER;
                else points[h][w] = Map.EMPTY;
            }
        }
    }

    /**
     * Metoda zwraca znak na mapie wskazywany przez punkt point
     * @param point punkt spod którego zostanie zwrócony znak mapy
     * @return zwraca znak mapy, na który wskazuje punkt
     */
    public char get(Point point){
        return points[point.y][point.x];
    }

    /**
     * @return zwraca szerokość mapy
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return zwraca wysokość mapy
     */
    public int getHeight() {
        return height;
    }

    /**
     * Metoda ustawia wszystkie te pola mapy, które nie są granicą, na wartość pola pustego
     */
    public void resetMap(){
        for(int h = 0; h < points.length; ++h){
            for(int w = 0; w < points[h].length; ++w){
                if(points[h][w] != Map.BORDER) points[h][w] = Map.EMPTY;
            }
        }
    }

    /**
     * Metoda wyszukuje wolną pozycję na mapie. Najpierw szuka w sposób losowy i gdy nie uda się znaleźć przez losowanie
     * to przeszukuje każdą pozycję na mapie.
     * @return zwracja punkt który na mapie ma wartość pola pustego
     * @throws IllegalStateException wyjątek jest wyrzucony gdy na mapie nie ma pustych pól
     */
    public Point getRandomEmptyPosition() throws IllegalStateException{
        // pick a random place
        int randomLimit=100, randomCounter=0, x, y;
        while(randomCounter < randomLimit){
            y = random.nextInt(0, points.length);
            x = random.nextInt(0, points[y].length);
            if(points[y][x] == Map.EMPTY){
                return new Point(x, y);
            }
            randomCounter++;
        }

        // if picking a random place was unsuccessful then search the whole map
        for(int h = 0; h < points.length; ++h){
            for(int w = 0; w < points[h].length; ++w){
                if(points[h][w] == Map.EMPTY){
                    return new Point(w, h);
                }
            }
        }

        // position not found - map has no space
        throw new IllegalStateException("Map has no empty space to place the object. Map is too small");
    }

    /**
     * Metoda wypisuje obecny stan mapy
     */
    public void drawMap(){
        for (char[] row : points) {
            for (char s : row) {
                System.out.print(s);
            }
            System.out.print('\n');
        }
    }

    /**
     * Metoda zaznacza na mapie pozycję przekazanego obiektu - metoda nie wyświetla mapy
     * @param object obiekt, który zostanie zaznaczony na mapie
     */
    public void drawObject(SimulationObject object){
        Point position = object.getPosition();
        points[position.y][position.x] = object.getSign();
    }
}
