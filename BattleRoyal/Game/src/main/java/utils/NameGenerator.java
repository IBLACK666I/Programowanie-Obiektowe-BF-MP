package utils;

public class NameGenerator {

    private NameGenerator(){}

    /**
     * Lista zawiera wszystkie nazwy w symulacji
     */
    private static final String []NAMES = new String[]{"Alexander","Oliver","Jake","Noah","James","Jack","Connor","Liam",
            "John","Harry","Callum","Mason","Robert","Jacob","Jacob","Jacob","Michael","Charlie","Kyle","William","William",
            "Thomas","Joe","Ethan","David","George","Reece","Michael","Richard","Oscar","Rhys","Joseph","James","Charlie",
            "James","Charles","William","Damian","Daniel","Thomas","Li","Smith","Smith","Smith","Jones","Wilson","Martin",
            "Brown","Gelbero","Wilson","Taylor"};

    /**
     * Indeks aktualnego imienia do wykorzystania
     */
    private static int INDEX = 0;

    /**
     * @return zwraca imię
     * @throws IllegalStateException wyjątek jest wyrzucony, gdy wszystkie imiona zostają wykorzystane
     */
    public static String get() throws IllegalStateException{
        if(INDEX >= NAMES.length) throw new IllegalStateException("There is not enough names to distinguish heroes");
        return NAMES[INDEX++];
    }
}
