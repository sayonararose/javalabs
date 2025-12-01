public class lab5 {
    public static void main(String[] args) {
        try {
            Album album = new Album("My Student Album", 8);

            album.add(new Song("Pink Venom", "BLACKPINK", 187, Genre.KPOP, "Fabulous..."));
            album.add(new Song("Kill Bill", "SZA", 210, Genre.RNB, "La la la..."));
            album.add(new Song("After Party", "Don Toliver", 195, Genre.HIPHOP, "Yeah yeah..."));
            album.add(new Instrumental("Blinding Lights Intro", "The Weeknd", 180, Genre.RNB, "synth, drum"));
            album.add(new Live("HUMBLE. Live", "Kendrick Lamar", 250, Genre.HIPHOP, "Staples Center"));
            album.add(new Song("Short Ambient", "Artist X", 50, Genre.AMBIENT, ""));

            album.printTracks();

            album.writeToFile("album.txt");

            System.out.println("Загальна тривалість (сек): " + album.totalDurationSeconds());

            album.sortByGenre();
            System.out.println("\nПісля сортування:");
            album.printTracks();

            Composition found = album.findFirstInRange(60, 240);
            System.out.println("\nЗнайдено: " + (found != null ? found : "нічого"));

        } catch (Exception e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}
