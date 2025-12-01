import java.util.ArrayList;

public class lab6 {
    public static void main(String[] args) {

        try {
            // --- 1. Використання Конструктора №1 (Порожній) ---
            System.out.println("\n1. Створюємо список порожнiм конструктором:");
            DoublyLinkedList<Composition> myPlaylist = new DoublyLinkedList<>();
            
            // Додаємо дані 
            myPlaylist.add(new Song("Pink Venom", "BLACKPINK", 187, Genre.KPOP, "Fabulous..."));
            myPlaylist.add(new Song("Kill Bill", "SZA", 210, Genre.RNB, "La la la..."));
            myPlaylist.add(new Song("After Party", "Don Toliver", 195, Genre.HIPHOP, "Yeah yeah..."));
            
            printCollection(myPlaylist);

            // --- 2. Використання Конструктора №2 з одним об'єктом ---
            System.out.println("\n2. Створюємо список конструктором з 1 об'єктом:");
            Instrumental intro = new Instrumental("Blinding Lights Intro", "The Weeknd", 180, Genre.RNB, "synth, drum");
            
            DoublyLinkedList<Composition> singleList = new DoublyLinkedList<>(intro);
            printCollection(singleList);

            // --- 3. Використання Конструктора №3 зі стандартної колекції ---
            System.out.println("\n3. Створюємо список конструктором зi стандартної колекцiї (ArrayList):");
            ArrayList<Composition> standardList = new ArrayList<>();
            standardList.add(new Live("HUMBLE. Live", "Kendrick Lamar", 250, Genre.HIPHOP, "Staples Center"));
            standardList.add(new Song("Short Ambient", "Artist X", 50, Genre.AMBIENT, ""));

            DoublyLinkedList<Composition> listFromCollection = new DoublyLinkedList<>(standardList);
            printCollection(listFromCollection);

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }

    private static void printCollection(DoublyLinkedList<Composition> list) {
        for (Composition c : list) {
            System.out.println(c);
        }
    }
}