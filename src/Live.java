public class Live extends Composition {
    private final String venue; 

    public Live(String title, String artist, int duration, Genre genre, String venue)
            throws InvalidDurationException {
        super(title, artist, duration, genre);
        this.venue = (venue == null ? "" : venue);
    }

    @Override
    public String toString() {
        return super.toString() + (venue.isEmpty() ? "" : " [Venue: " + venue + "]");
    }
}