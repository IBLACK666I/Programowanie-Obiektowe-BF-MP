package core;

import utils.*;

import java.io.IOException;
import java.util.*;

/**
 * Klasa reprezentuje symulację
 */
public class Simulation {

    /**
     * Enum, który reprezentuje obecny status symulacji
     */
    public enum Status{
        running, paused, interrupted, ended
    }

    /**
     * Obiekt używany przy losowaniu liczb
     */
    private final Random random;

    /**
     * Mapa symulacji
     */
    private final Map map;

    /**
     * Lista wszystkich obiektów symulacji
     */
    private final List<SimulationObject> objects;

    /**
     * Obiekt, który służy do liczenia statystyk symulacji
     */
    private final StatsCounter stats;

    /**
     * Maksymalny czas trwania symulacji
     */
    private final long maxTime;

    /**
     * Nazwa pliku, do którego zostaną zapisane statystyki
     */
    private final String statsFileName;

    /**
     * Obecny status symulacji
     */
    private Status status;

    /**
     * Konstruktor tworzy symulacje
     * @param map mapa symulacji
     * @param initHeros początkowa liczba bohaterów
     * @param initHealthObjects liczba punktów życia
     * @param initStrengthObjects liczba punktów siły
     * @param initHealth ilość zdrowia każdego bohatera
     * @param initStrength ilość siły każdego bohatera
     * @param beginSpeedScope dolna granica wartosci szybkości każdego bohatera
     * @param endSpeedScope górna granica wartosci szybkości każdego bohatera
     * @param maxTime maksymalny czas symulacji
     * @param statsFileName plik do którego zostaną zapisane statystyki symulacji
     * @param seed seed dla generatora liczb losowych
     * @throws IllegalStateException wyjątek jest wyrzucony, gdy mapa jest zbyt mała, aby pomieścić wszystkie obiekty
     */
    public Simulation(Map map, int initHeros, int initHealthObjects, int initStrengthObjects, int initHealth, int initStrength, int beginSpeedScope, int endSpeedScope,  long maxTime, String statsFileName, int seed) throws IllegalStateException {
        if(seed < 0) this.random = new Random();
        else this.random = new Random(seed);
        this.map = map;
        this.objects = new ArrayList<>();

        for(int i=0; i<initHeros; ++i) {
            int speed = random.nextInt(beginSpeedScope, endSpeedScope);
            objects.add(new Hero(NameGenerator.get(), map.getRandomEmptyPosition(), UniqueCharacterGenerator.get(), initHealth, initStrength, speed));
        }
        for(int i=0; i<initHealthObjects; ++i){
            objects.add(new HealthObject(map.getRandomEmptyPosition()));
        }
        for(int i=0; i<initStrengthObjects; ++i){
            objects.add(new StrengthObject(map.getRandomEmptyPosition()));
        }
        this.stats = new StatsCounter();

        this.maxTime = maxTime;
        this.statsFileName = statsFileName;
        this.status = Status.paused;
    }

    /**
     * metoda ustawia status symulacji na zatrzymany
     */
    public void pause(){
        status = Status.paused;
    }

    /**
     * metoda ustawia status symulacji na uruchomiony
     */
    public void unpause(){
        status = Status.running;
    }

    /**
     * metoda ustawia status symulacji na przerwany
     */
    public void interrupt(){
        status = Status.interrupted;
    }

    /**
     * metoda ustawia status symulacji na zakończony
     */
    public void end(){
        status = Status.ended;
    }

    /**
     * @return zwraca obecny status symulacji
     */
    public Status getStatus(){
        return status;
    }

    /**
     * @return zwraca wszystkie obiekty znajdujące się w symulacji
     */
    public List<SimulationObject> getObjects() {
        return objects;
    }

    /**
     * Metoda uruchamia symulacje i kontroluje jej przebieg
     * @throws InterruptedException wyjątek jest wyrzucony, gdy metoda sleep zostanie przerwana
     * @throws IOException wyjątek jest wyrzucony, gdy podczas zapisywania statystyk symulacji wystąpi jakiś błąd
     */
    public void run() throws InterruptedException, IOException {
        TimeCounterThread time = new TimeCounterThread(this);
        AsyncKeyStateThread keyState = new AsyncKeyStateThread(this);

        time.start(); // start licznika watkow
        keyState.start(); // starting the async key state thread

        // poczatkowe rysowanie obietkow
        for(SimulationObject object : objects) map.drawObject(object);

        // zapisywanie stanu poczatkowego
        stats.note(this);

        while(true) {
            // czysci ekran
            clearScreen();
            // uruchamia symulacje
            while (status == Status.running) {
                // resetuje mape
                map.resetMap();

                // tworz obiekty
                for (SimulationObject object : objects) map.drawObject(object);

                // generuje ruch
                List<SimulationObject> toRemove = new ArrayList<>();
                for (SimulationObject object : objects) {
                    // sprawdzy czy obiekt jest bohaterem - tylko bohater moze sie poruszac
                    if (object instanceof Hero hero) {

                        // generuje pozycje dla bohatera
                        Point newPosition = hero.generateMove();
                        // sprawdza czy pozycja jest wewnatrz mapy
                        if(newPosition.x > 0 && newPosition.x < map.getWidth()-1 && newPosition.y > 0 && newPosition.y < map.getHeight()-1) {
                            // sprawdza co jest na mapie na danej pozycji
                            char sign = map.get(newPosition);
                            if(sign == Map.EMPTY) { // jezeli potencjalna pozycja wskazuje na puste pole to przenies tam ten obiekt
                                hero.setNewPosition(newPosition);
                            }else if(sign == Map.STRENGTH_POINT) { // bohater otrzymuje dodatkowe punkty sily
                                if(findObjectByPosition(newPosition) instanceof StrengthObject strengthPoint){
                                    strengthPoint.setNewPosition(map.getRandomEmptyPosition());
                                    hero.setNewPosition(newPosition);
                                    hero.increaseStrength();
                                }
                            }else if(sign == Map.HEALTH_POINT) { // bohater otrzymuje dodatkowe punkty zdrowia
                                if(findObjectByPosition(newPosition) instanceof HealthObject healthPoint){
                                    healthPoint.setNewPosition(map.getRandomEmptyPosition());
                                    hero.setNewPosition(newPosition);
                                    hero.increaseHealth();
                                }
                            }else if(sign != Map.BORDER) { // bohater atakuje innego bohatera
                                if(findObjectByPosition(newPosition) instanceof Hero attackedHero){
                                    hero.attack(attackedHero);
                                    if(!attackedHero.isAlive()) toRemove.add(attackedHero);
                                }
                            }
                        }
                    }
                }

                // zapisywanie statystyk
                stats.note(this);

                // usuwanie martwych bohaterow
                objects.removeAll(toRemove);

                //rysowanie mapy
                map.drawMap();

                // rysowanie menu
                printMenu("Simulation is running for: " + time.getTime() + " seconds");

                // ustawianie kursora na lewy gorny rog
                setCursor(0, 0);

                // koniec symulacji
                if (time.getTime() >= maxTime) status = Status.ended;

                // opoznienie
                Thread.sleep(39);
            }
            // czyszczenie ekranu
            clearScreen();

            // symulacja zatrzymana
            while (status == Status.paused) {
                // rysuj mape
                map.drawMap();

                // rysuj menu
                printMenu("Simulation is paused");

                // poczekaj az symulacja zostanie wznowiona
                while (status == Status.paused) {
                    Thread.sleep(100);
                }
            }

            // symulacja jest zakończona lub przerwana
            if (status == Status.ended || status == Status.interrupted) break;
        }

        // czekanie na zakonczenie watkow
        System.out.println("Waiting for threads to die... (press enter if the threads did not die)");
        time.join();
        keyState.join();

        // zakonczenie symulacji
        if (status == Status.ended){
            System.out.println("The simulation was ended");
            if(statsFileName != null){
                stats.save(statsFileName);
            }else{
                stats.dump();
            }
        }
        else if(status == Status.interrupted){
            throw new InterruptedException("The simulation was interrupted");
        }
    }

    /**
     * Metoda czyści ekran
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Metoda ustawia kursor we wskazanym miejscu w konsoli
     * @param row rząd w którym kursor zostanie ustawiony
     * @param column kolumna w której kursor zostanie ustawiony
     */
    public static void setCursor(int row, int column) {
        char escCode = 0x1B;
        System.out.print(String.format("%c[%d;%df",escCode,row,column));
    }

    /**
     * Metoda odnajduje obiekt w symulacji, który znajduje się we wskazanej pozycji
     * @param position pozycja z której obiekt zostanie wyszukany
     * @return zwraca obiekt spod wskazanej pozycji
     */
    private SimulationObject findObjectByPosition(Point position){
        for(SimulationObject object : objects){
            if(object.position.x == position.x && object.position.y == position.y){
                return object;
            }
        }
        return null;
    }

    /**
     * Metoda wypisuje menu symulacji
     * @param header nagłówek, który zostanie wypisany przez wypisaniem menu
     */
    private static void printMenu(String header){
        System.out.println(header);
        System.out.println("Menu: ");
        System.out.println("1 - pause");
        System.out.println("2 - unpause");
        System.out.println("3 - end");
        System.out.println("4 - interrupt");
    }
}