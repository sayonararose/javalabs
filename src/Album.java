import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Album {
    private final String title;             // Назва альбому 
    private final Composition[] tracks;     // Масив композицій 
    private int count = 0;            // Кількість доданих треків

    // Конструктор альбому з вказанням місткості
    public Album(String title, int capacity) {
        if (title == null || title.trim().isEmpty()) title = "Untitled";
        this.title = title;
        if (capacity <= 0) capacity = 10;
        tracks = new Composition[capacity];
    }

    // Додавання композиції в альбом
    public void add(Composition c) throws AlbumFullException {
        if (c == null) return;
        if (count >= tracks.length) throw new AlbumFullException("Альбом заповнений");
        tracks[count++] = c;
    }

    // Підрахунок загальної тривалості у секундах
    public int totalDurationSeconds() {
        int sum = 0;
        for (int i = 0; i < count; i++) sum += tracks[i].duration;
        return sum;
    }

    // Запис альбому у файл
    public void writeToFile(String filename) throws IOException {
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
    public String formatTotalDuration() {
        int secs = totalDurationSeconds();
        int m = secs / 60;
        int s = secs % 60;
        return String.format("%d:%02d", m, s);
    }

    // Сортування треків за жанром
    public void sortByGenre() {
        Arrays.sort(tracks, 0, count, Comparator.comparingInt(a -> a.genre.ordinal()));
    }

    // Пошук першої композиції у вказаному діапазоні тривалості
    public Composition findFirstInRange(int minSec, int maxSec) {
        for (int i = 0; i < count; i++) {
            int d = tracks[i].duration;
            if (d >= minSec && d <= maxSec) return tracks[i];
        }
        return null;
    }

    // Вивід усіх треків альбому у консоль
    public void printTracks() {
        System.out.println("Album: " + title);
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ") " + tracks[i].toString());
        }
        System.out.println("Total: " + formatTotalDuration());
    }
}