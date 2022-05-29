package app;

import core.Map;
import core.Simulation;
import utils.Configurator;

import java.io.IOException;

public class Main {

    /**
     * Główna metoda z której uruchamiane jest całe środowisko testowe symulacji
     * @param args argumenty wywołania programu (patrz dokumentacja)
     */
    public static void main(String[] args) {
        try{
            Configurator configurator = new Configurator(args);
            Simulation simulation = new Simulation(
                    new Map(configurator.getMapWidth(), configurator.getMapHeight()),
                    configurator.getInitHeroCount(),
                    configurator.getHealthObjectCount(),
                    configurator.getStrengthObjectCount(),
                    configurator.getInitHealthValue(),
                    configurator.getInitStrengthValue(),
                    configurator.getLowerSpeedBound(),
                    configurator.getUpperSpeedBound(),
                    configurator.getSimulationTime(),
                    configurator.getFileForStats() ,
                    configurator.getSeed());
            simulation.run();
        }
        catch (IllegalStateException | IllegalArgumentException e){
            System.out.println("Wrong argument was passed: " + e.getMessage());
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println("Error related to saving the simulation result. Message: " + e.getMessage());
        }
        catch (Exception e){
            System.out.println("Unexpected error occurred. Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
