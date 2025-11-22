//Варіант 18
//Визначити ієрархію музичних композицій. Записати на диск альбом. Порахувати тривалість альбому. Провести перестановку композицій диска на основі приналежності до стилю. Знайти композицію, що відповідає заданому діапазону довжини треків.
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class lab5 {

    // жанри музики
    enum Genre {
        POP, ROCK, JAZZ, KPOP, ELECTRONIC, AMBIENT, RNB, HIPHOP, OTHER
    }

    //клас композиції
    static class Composition {
        String title;    // Назва композиції
        String artist;   // Виконавець
        int duration;    // Тривалість у секундах
        Genre genre;     // Жанр композиції

        // Конструктор з перевіркою правильності даних
        Composition(String title, String artist, int duration, Genre genre) throws InvalidDurationException {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Title не може бути порожнім");
            }
            if (duration <= 0) {
                throw new InvalidDurationException("Duration має бути > 0");
            }
            this.title = title.trim();
            this.artist = (artist == null ? "Unknown" : artist.trim());
            this.duration = duration;
            this.genre = (genre == null ? Genre.OTHER : genre);
        }

        // Перетворення тривалості у формат "хв:сек"
        String durationAsString() {
            int m = duration / 60;
            int s = duration % 60;
            return String.format("%d:%02d", m, s);
        }

        // Повертає текстове представлення композиції
        @Override
        public String toString() {
            return title + " — " + artist + " [" + genre + "] (" + durationAsString() + ")";
        }
    }

    // Підклас композиції, пісня з уривком тексту
    static class Song extends Composition {
        String lyricsSnippet;

        Song(String title, String artist, int duration, Genre genre, String lyricsSnippet)
                throws InvalidDurationException {
            super(title, artist, duration, genre);
            this.lyricsSnippet = (lyricsSnippet == null ? "" : lyricsSnippet);
        }

        @Override
        public String toString() {
            return super.toString() + (lyricsSnippet.isEmpty() ? "" : " [Lyrics: " + lyricsSnippet + "]");
        }
    }

    // Підклас композиції, інструментал з переліком інструментів
    static class Instrumental extends Composition {
        String instruments;

        Instrumental(String title, String artist, int duration, Genre genre, String instruments)
                throws InvalidDurationException {
            super(title, artist, duration, genre);
            this.instruments = (instruments == null ? "" : instruments);
        }

        @Override
        public String toString() {
            return super.toString() + (instruments.isEmpty() ? "" : " [Instr: " + instruments + "]");
        }
    }

    // Підклас композиції, live виступ з місцем проведення
    static class Live extends Composition {
        String venue;

        Live(String title, String artist, int duration, Genre genre, String venue) throws InvalidDurationException {
            super(title, artist, duration, genre);
            this.venue = (venue == null ? "" : venue);
        }

        @Override
        public String toString() {
            return super.toString() + (venue.isEmpty() ? "" : " [Venue: " + venue + "]");
        }
    }

    // Виключення для некоректної тривалості композиції
    static class InvalidDurationException extends Exception {
        InvalidDurationException(String msg) {
            super(msg);
        }
    }

    // Виключення для переповненого альбому
    static class AlbumFullException extends Exception {
        AlbumFullException(String msg) {
            super(msg);
        }
    }

    // Клас альбому
    static class Album {
        String title;             // Назва альбому
        Composition[] tracks;     // Масив композицій
        int count = 0;            // Кількість доданих треків

        // Конструктор альбому з вказанням місткості
        Album(String title, int capacity) {
            if (title == null || title.trim().isEmpty()) title = "Untitled";
            this.title = title;
            if (capacity <= 0) capacity = 10;
            tracks = new Composition[capacity];
        }

        // Додавання композиції в альбом
        void add(Composition c) throws AlbumFullException {
            if (c == null) return;
            if (count >= tracks.length) throw new AlbumFullException("Альбом заповнений");
            tracks[count++] = c;
        }

        // Підрахунок загальної тривалості у секундах
        int totalDurationSeconds() {
            int sum = 0;
            for (int i = 0; i < count; i++) sum += tracks[i].duration;
            return sum;
        }

        // Запис альбому у файл
        void writeToFile(String filename) throws IOException {
            try (FileWriter fw = new FileWriter(filename)) {
                fw.write("Album: " + title + "\n");
                fw.write("Tracks: " + count + "\n");
                fw.write("Total duration: " + formatTotalDuration() + "\n\n");
                for (int i = 0; i < count; i++) {
                    fw.write((i + 1) + ". " + tracks[i].toString() + "\n");
                }
            }
        }

        // Форматування загальної тривалості у "хв:сек"
        String formatTotalDuration() {
            int secs = totalDurationSeconds();
            int m = secs / 60;
            int s = secs % 60;
            return String.format("%d:%02d", m, s);
        }

        // Сортування треків за жанром
        void sortByGenre() {
            Arrays.sort(tracks, 0, count, Comparator.comparingInt(a -> a.genre.ordinal()));
        }

        // Пошук першої композиції у вказаному діапазоні тривалості
        Composition findFirstInRange(int minSec, int maxSec) {
            for (int i = 0; i < count; i++) {
                int d = tracks[i].duration;
                if (d >= minSec && d <= maxSec) return tracks[i];
            }
            return null;
        }

        // Вивід усіх треків альбому у консоль
        void printTracks() {
            System.out.println("Album: " + title);
            for (int i = 0; i < count; i++) {
                System.out.println((i + 1) + ") " + tracks[i].toString());
            }
            System.out.println("Total: " + formatTotalDuration());
        }
    }

    public static void main(String[] args) {
        try {
            // Створюємо альбом
            Album album = new Album("My Student Album", 8);

            // Додаємо композиції
            album.add(new Song("Pink Venom", "BLACKPINK", 187, Genre.KPOP, "Fabulous..."));
            album.add(new Song("Kill Bill", "SZA", 210, Genre.RNB, "La la la..."));
            album.add(new Song("After Party", "Don Toliver", 195, Genre.HIPHOP, "Yeah yeah..."));
            album.add(new Instrumental("Blinding Lights Intro", "The Weeknd", 180, Genre.RNB, "synth, drum"));
            album.add(new Live("HUMBLE. Live", "Kendrick Lamar", 250, Genre.HIPHOP, "Staples Center"));
            album.add(new Song("Short Ambient", "Artist X", 50, Genre.AMBIENT, ""));

            // Вивід усіх треків
            album.printTracks();

            // Запис альбому у файл
            String file = "album.txt";
            album.writeToFile(file);
            System.out.println("Альбом записано у файл: " + file);

            // Вивід загальної тривалості у секундах
            System.out.println("Загальна тривалість (сек): " + album.totalDurationSeconds());

            // Сортування треків за жанром та вивід
            album.sortByGenre();
            System.out.println("\nПісля сортування за жанром:");
            album.printTracks();

            // Пошук треку у діапазоні тривалості
            int min = 60, max = 240;
            Composition found = album.findFirstInRange(min, max);
            if (found != null) {
                System.out.println("\nЗнайдено композицію у діапазоні " + min + "-" + max + " с: " + found);
            } else {
                System.out.println("\nНе знайдено композицій у діапазоні " + min + "-" + max + " с");
            }

        } catch (AlbumFullException e) {
            System.err.println("Не вдалося додати трек: " + e.getMessage());
        } catch (InvalidDurationException e) {
            System.err.println("Некоректна тривалість: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Помилка запису у файл: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Невідома помилка: " + e.getMessage());
        }
    }
}
