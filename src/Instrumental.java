public class Instrumental extends Composition {
    private final String instruments; 

    public Instrumental(String title, String artist, int duration, Genre genre, String instruments)
            throws InvalidDurationException {
        super(title, artist, duration, genre);
        this.instruments = (instruments == null ? "" : instruments);
    }

    @Override
    public String toString() {
        return super.toString() + (instruments.isEmpty() ? "" : " [Instr: " + instruments + "]");
    }
}