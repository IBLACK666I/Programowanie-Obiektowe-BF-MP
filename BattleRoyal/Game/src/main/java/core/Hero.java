package core;

import utils.Point;

import java.util.Random;

/**
 * Klasa reprezentuje bohatera w symulacji
 */
public class Hero extends SimulationObject{

    /**
     * Obiekt używany przy losowaniu liczb
     */
    private final Random random;

    /**
     * nazwa danego bohatera
     */
    private final String name;

    /**
     * ilość życia bohatera
     */
    private int health;

    /**
     * ilość siły bohatera
     */
    private int strength;

    /**
     * szybkość bohatera
     */
    private final int speed;

    /**
     * Konstruktor tworzy obiekt bohatera
     * @param name nazwa danego bohatera
     * @param initPosition początkowa pozycja bohatera
     * @param sign znak reprezentujący bohatera
     * @param health ilość życia bohatera
     * @param strength ilość siły bohatera
     * @param speed szybkość bohatera
     */
    public Hero(String name, Point initPosition, char sign, int health, int strength, int speed) {
        super(initPosition, sign);
        this.random = new Random();
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.speed = speed;
    }

    /**
     * @return zwraca nazwe
     */
    public String getName() {
        return name;
    }

    /**
     * @return zwraca ilość życia
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return zwraca ilość siły
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Metoda generuje losową pozycję dla bohatera opierając się na aktualnej pozycji. Metoda nie modyfikuje pozycji bohatera
     * @return zwraca wygenerowaną pozycję bohatera
     */
    public Point generateMove(){
        Point newPosition = new Point(position.x, position.y);

        if(random.nextInt() % 2 == 0){ // move horizontal
            if(random.nextInt() % 2 == 0){ // move left
                newPosition.x -= speed;
            }else{ // move right
                newPosition.x += speed;
            }
        }else{ // move vertical
            if(random.nextInt() % 2 == 0){ // move up
                newPosition.y -= speed;
            }else{ // move right
                newPosition.y += speed;
            }
        }

        return newPosition;
    }

    /**
     * @return zwraca true jest bohater jest zywy w przeciwnym przypadku false
     */
    public boolean isAlive(){
        return health > 0;
    }

    /**
     * Metoda wykonuje atak na innego bohatera
     * @param other bohater, który zostanie zaatakowany
     */
    public void attack(Hero other){
        other.health -= strength;
    }

    /**
     * Metoda zwiększa ilość siły o 5 punktów
     */
    public void increaseStrength(){
        strength += 5;
    }

    /**
     * Metoda zwiększa ilość życia o 5 punktów
     */
    public void increaseHealth(){
        strength += 5;
    }
}
