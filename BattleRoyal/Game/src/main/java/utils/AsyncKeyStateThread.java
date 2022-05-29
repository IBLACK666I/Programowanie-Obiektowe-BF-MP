package utils;

import core.Simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AsyncKeyStateThread extends Thread{

    /**
     * Referencja do obiektu symulacji
     */
    private final Simulation simulation;

    /**
     * Konstruktor klasy
     * @param simulation ustawia referencje symulacji
     */
    public AsyncKeyStateThread(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Metoda nasłuchuje kliknięć klawiszy które są potwierdzone enterem
     */
    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in))){
            while(simulation.getStatus() != Simulation.Status.interrupted && simulation.getStatus() != Simulation.Status.ended){
                String buffer = in.readLine();
                if(buffer.equals("1") && simulation.getStatus() != Simulation.Status.paused){
                    simulation.pause();
                }
                else if(buffer.equals("2") && simulation.getStatus() != Simulation.Status.running){
                    simulation.unpause();
                }
                else if(buffer.equals("3")){
                    simulation.end();
                }
                else if(buffer.equals("4")){
                    simulation.interrupt();
                }
            }
        }catch (IOException e){
            System.out.println("Error in thread [" + currentThread().getName() + "]. Message: " + e.getMessage());
        }
    }
}
