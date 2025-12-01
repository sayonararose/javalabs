public class Song extends Composition {
    private final String lyricsSnippet; 

    public Song(String title, String artist, int duration, Genre genre, String lyricsSnippet)
            throws InvalidDurationException {
        super(title, artist, duration, genre);
        this.lyricsSnippet = (lyricsSnippet == null ? "" : lyricsSnippet);
    }

    @Override
    public String toString() {
        return super.toString() + (lyricsSnippet.isEmpty() ? "" : " [Lyrics: " + lyricsSnippet + "]");
    }
}