package de.afp.db_kino.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.ObjectUtils;

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

    public List<Film> getFilmsByName(String name) throws SQLException {
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection
                    .prepareStatement("SELECT * FROM film WHERE titel LIKE \"%" + name + "%\"")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Film film = new Film(resultSet.getLong("film_id"), resultSet.getString("titel"),
                            resultSet.getInt("dauer"), resultSet.getInt("fsk_freigabe"),
                            resultSet.getString("inhalt"),
                            LocalDate.parse(resultSet.getDate("erscheinungsdatum").toString()));
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

                statement.execute();
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
    }

    public Film getFilmById(Long id) throws SQLException {
        Film film = new Film();
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "SELECT * FROM film WHERE film_id =" + id)) {
                Optional<ResultSet> resultSet = Optional.ofNullable(statement.executeQuery());
                if (resultSet.get().next()) {
                    film = new Film(resultSet.get().getLong("film_id"), resultSet.get().getString("titel"),
                            resultSet.get().getInt("dauer"), resultSet.get().getInt("fsk_freigabe"),
                            resultSet.get().getString("inhalt"),
                            LocalDate.parse(resultSet.get().getDate("erscheinungsdatum").toString()));
                    return film;
                } else {
                    throw new Error();
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        } catch (Error e) {
            System.err.println("Kein Film mit der Id: " + id + " gefunden");
        } finally {
            dbConfig.closeDatabaseConnection();
        }
        return null;
    }

    public Optional<Film> updateFilm(Long id, String attr, String value) throws SQLException {
        Optional<Film> originalFilm = Optional.ofNullable(getFilmById(id));
        Object newValue = new Object();
        if (!value.isBlank() && !attr.isBlank()) {
            if (originalFilm.isPresent()) {
                switch (attr) {
                    case "titel" -> newValue = "\"".concat(value).concat("\"");
                    case "dauer" -> newValue = Integer.parseInt(value);
                    case "fsk_freigabe" -> newValue = Integer.parseInt(value);
                    case "inhalt" -> newValue = "\"".concat(value).concat("\"");
                    case "erscheinungsdatum" -> newValue = "\"".concat(value).concat("\"");
                    default -> throw new Error("kein gueltiges Attribut");
                }
                try {
                    dbConfig.initDatabaseConnection();
                    try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                            "Update film Set " + attr + "=" + newValue + " WHERE film_id=" + id)) {
                        statement.execute();
                        originalFilm = Optional.ofNullable(getFilmById(id));
                        return originalFilm;
                    }
                } catch (SQLException e) {
                    System.err.println(e);
                } catch (Error e) {
                    System.err.println(e.getMessage());
                }
            } else {
                throw new Error("Kein Film in der Datenbank mit der ID: " + id);
            }
        } else {
            throw new Error("Bitte keine leeren Felder angeben");
        }
        return originalFilm;
    }

    public String deleteFilm(Long id) throws SQLException {
        Optional<Film> originalFilm = Optional.ofNullable(getFilmById(id));
        if (originalFilm.isPresent()) {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "DELETE FROM film WHERE film_id =" + id)) {
                statement.execute();
                return "Film erfolgreich gelöscht";
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                dbConfig.closeDatabaseConnection();
            }
        } else if (originalFilm.isEmpty()) {

            return "kein Film mit der ID" + id + "vorhanden";

        }
        return "Löschen nicht möglich";
    }
}
