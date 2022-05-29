package utils;

public class UniqueCharacterGenerator {

    private UniqueCharacterGenerator(){}

    /**
     * Tablica z unikalnymi znakami
     */
    private static final char []CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Indeks obecnie wykorzysywanego znaku
     */
    private static int INDEX = 0;

    /**
     * @return zwraca unikalny znak
     * @throws IllegalStateException wyjątek jest wyrzucony gdy nie ma już wolnych znaków
     */
    public static char get() throws IllegalStateException{
        if(INDEX >= CHARS.length) throw new IllegalStateException("There is not enough unique characters to distinguish heroes");
        return CHARS[INDEX++];
    }
}
