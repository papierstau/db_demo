package de.afp.db_kino.model;

public class Genre {
    private Long genreId;
    private String genre;

    public Genre() {}

    public Genre(String genre){
        this.genre = genre;
    }

    public Genre(Long genreId, String genre) {
        this.genreId = genreId;
        this.genre = genre;
    }

    public Long getGenreId() {
        return this.genreId;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
}
