public class Composition {
    protected String title;
    protected String artist;
    protected int duration;
    protected Genre genre;

    public Composition(String title, String artist, int duration, Genre genre)
            throws InvalidDurationException {

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

    public String durationAsString() {
        int m = duration / 60;
        int s = duration % 60;
        return String.format("%d:%02d", m, s);
    }

    @Override
    public String toString() {
        return title + " — " + artist + " [" + genre + "] (" + durationAsString() + ")";
    }
}
