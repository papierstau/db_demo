package de.afp.db_kino.model;

import java.time.LocalDate;

public class Film {
    private Long film_id;
    private String titel;
    private int dauer;
    private int fsk_freigabe;
    private String inhalt;
    private LocalDate erscheinungsdatum;

    
    public Film() {
    }

    public Film(String titel, int dauer, int fsk_freigabe, String inhalt, LocalDate erscheinungsdatum) {
        this.titel = titel;
        this.dauer = dauer;
        this.fsk_freigabe = fsk_freigabe;
        this.inhalt = inhalt;
        this.erscheinungsdatum = erscheinungsdatum;
    }

    public Film(Long film_id, String titel, int dauer, int fsk_freigabe, String inhalt, LocalDate erscheinungsdatum) {
        this.film_id = film_id;
        this.titel = titel;
        this.dauer = dauer;
        this.fsk_freigabe = fsk_freigabe;
        this.inhalt = inhalt;
        this.erscheinungsdatum = erscheinungsdatum;
    }

    public Long getFilmId() {
        return this.film_id;
    }

    public String getTitel() {
        return this.titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getDauer() {
        return this.dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public int getFskFreigabe() {
        return this.fsk_freigabe;
    }

    public void setFskFreigabe(int fsk_freigabe) {
        this.fsk_freigabe = fsk_freigabe;
    }

    public String getInhalt() {
        return this.inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public LocalDate getErscheinungsdatum() {
        return this.erscheinungsdatum;
    }

    public void setErschungsdatum(LocalDate date) {
        this.erscheinungsdatum = date;
    }

    @Override
    public String toString() {
        return "FilmID: " + this.getFilmId() + " Titel: " + this.getTitel() +
                " Dauer: " + this.getDauer() + " FSK-Freigabe: " + this.getFskFreigabe() +
                " Inhalt: " + this.getInhalt() + " Erscheinungsdatum " + this.getErscheinungsdatum();
    }
}
