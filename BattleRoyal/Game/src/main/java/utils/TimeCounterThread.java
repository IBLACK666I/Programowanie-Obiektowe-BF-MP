package utils;

import core.Simulation;

/**
 * Klasa liczy czas w symulacji
 */
public class TimeCounterThread extends Thread{

    /**
     * Aktualny czas
     */
    private long time;

    /**
     * Symulacja dla której czas jest liczony
     */
    private final Simulation simulation;

    /**
     * Konstruktor ustawia licznik czasu
     * @param simulation symulacja dla której czas będzie liczony
     */
    public TimeCounterThread(Simulation simulation) {
        this.time = 0;
        this.simulation = simulation;
    }

    /**
     * @return zwraca obecny czas
     */
    public long getTime() {
        return time;
    }

    /**
     * Metoda liczy czas w zależności od statusu symulacji
     */
    @Override
    public void run() {
        try{
            while(simulation.getStatus() != core.Simulation.Status.interrupted && simulation.getStatus() != core.Simulation.Status.ended){
                Thread.sleep(1000);
                if(simulation.getStatus() == Simulation.Status.running) ++time;
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
