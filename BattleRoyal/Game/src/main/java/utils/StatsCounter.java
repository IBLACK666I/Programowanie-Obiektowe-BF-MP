package utils;

import core.Hero;
import core.Simulation;
import core.SimulationObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentuje licznik statystyk
 */
public class StatsCounter {

    /**
     * Lista przechowuje kolejne linie opisujące statystyki symulacji - każda linia zawiera informacje na temat 1 iteracji
     */
    private final List<String> iterationDumps;

    /**
     * Licznik iteracji
     */
    private int iterationCount;

    /**
     * Konstruktor klasy
     */
    public StatsCounter() {
        this.iterationCount = 1;
        this.iterationDumps = new ArrayList<>();
    }

    /**
     * Metoda pobiera aktualne dane z kolejnej iteracji symulacji
     * @param simulation symulacja, która zastanie zanotowana
     */
    public void note(Simulation simulation){
        StringBuilder line = new StringBuilder("Iteration=" + (iterationCount++) + " ");

        int aliveCount = 0, deadCount = 0;
        for(SimulationObject object : simulation.getObjects()){
            if(object instanceof Hero hero){
                if(hero.isAlive()) ++aliveCount;
                else ++deadCount;

                line.append("[name=").append(hero.getName()).append(" health=").append(hero.getHealth()).append(" strength=").append(hero.getStrength()).append("] ");
            }
        }
        line.append("alive=").append(aliveCount).append(" died=").append(deadCount);
        iterationDumps.add(line.toString());
    }

    /**
     * Metoda zapisuje statystyki symulacji do pliku
     * @param filename plik do którego zostaną zapisane informacje
     * @throws IOException wyjątek jest wyrzucony gdy wystapi błąd zapisu
     */
    public void save(String filename) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            writer.write("Stats for the simulation\n");
            for(String line : iterationDumps){
                writer.write(line);
                writer.write('\n');
            }
        }
    }

    /**
     * Metoda wyświetla statystyki w konsoli
     */
    public void dump(){
        System.out.println("Stats for the simulation:");
        for(String line : iterationDumps){
            System.out.println(line);
        }
    }
}
