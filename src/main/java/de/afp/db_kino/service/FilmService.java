package de.afp.db_kino.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.afp.db_kino.config.DBConfig;
import de.afp.db_kino.model.Film;

public class FilmService {
    private DBConfig dbConfig = new DBConfig();
    private List<Film> filme = new ArrayList<>();

    public List<Film> getFilms() throws SQLException {
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement("""
                    	SELECT *
                    	FROM film
                    """)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Film film = new Film(resultSet.getLong("film_id"), resultSet.getString("titel"),
                            resultSet.getInt("dauer"), resultSet.getInt("fsk_freigabe"),
                            resultSet.getString("inhalt"),
                            LocalDate.parse(resultSet.getDate("erscheinungsdatum").toString()));

                    // String val1 = resultSet.getString(2);
                    // String inhalt = resultSet.getString("inhalt");
                    // int val2 = resultSet.getInt("film_id");
                    // System.out.println(val2 + ": " + val1 + " inhalt: " + inhalt);
                    filme.add(film);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
        return filme;
    }

    public void saveFilm(Film film) throws SQLException {
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "INSERT INTO film(titel, dauer, fsk_freigabe, inhalt, erscheinungsdatum) VALUES(\""
                            + film.getTitel() + "\","
                            + film.getDauer() + ","
                            + film.getFskFreigabe() + ","
                            + "\"" + film.getInhalt() + "\","
                            + "\"" + film.getErscheinungsdatum()
                            + "\")")) {

                System.out.println(statement);
                statement.execute();
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
    }
}
