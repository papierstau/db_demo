package de.afp.db_kino.model;

public class Genre {
    private Long genreId;
    private String genreName;

    public Genre() {
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre(Long genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Long getGenreId() {
        return this.genreId;
    }

    public String getGenreName() {
        return this.genreName;
    }

    public void setGenreName(String genre) {
        this.genreName = genre;
    }

    @Override
    public String toString() {
        return "GenreId: " + this.getGenreId() + " Genre: " + this.getGenreName();
    }

}
