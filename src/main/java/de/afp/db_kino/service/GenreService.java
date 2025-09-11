package de.afp.db_kino.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.afp.db_kino.config.DBConfig;
import de.afp.db_kino.model.Genre;

public class GenreService {
    private DBConfig dbConfig = new DBConfig();
    private List<Genre> genre = new ArrayList<>();

    public List<Genre> getGenres() throws SQLException {
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement("""
                    Select *
                    From genre

                        """)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Genre gen = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre"));
                    genre.add(gen);
                }

            } catch (Exception e) {
                System.err.println(e);
            } finally {
                dbConfig.closeDatabaseConnection();
            }
        } catch (Error e) {
        }
        return genre;
    }

    public Genre getGenreById(Long id) throws SQLException {
        Genre genre = new Genre();
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "SELECT * FROM genre WHERE genre_id=" + id)) {
                Optional<ResultSet> resultSet = Optional.ofNullable(statement.executeQuery());
                if (resultSet.get().next()) {
                    genre = new Genre(resultSet.get().getLong("genre_id"),
                            resultSet.get().getString("genre".toString()));
                    return genre;
                } else {
                    throw new Error();
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        } catch (Error e) {
            System.err.println("Kein Genre mit der Id: " + id + "gefunden");
        } finally {
            dbConfig.closeDatabaseConnection();
        }
        return null;
    }

    public void saveGenre(Genre genre) throws SQLException {
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "INSERT INTO genre(genreName) VALUES(\""
                            + genre.getGenreName()
                            + "\")")) {
                statement.execute();
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
    }

    public Optional<Genre> updateGenre(Long id, String attr, String value) throws SQLException {
        Optional<Genre> originalGenre = Optional.ofNullable(getGenreById(id));
        Object newValue = new Object();
        if (!value.isBlank() && !attr.isBlank()) {
            if (originalGenre.isPresent()) {
                switch (attr) {
                    case "genreName" -> newValue = "\"".concat(value).concat("\"");
                    default -> throw new Error("kein gueltiges Attribut");
                }
                try {
                    dbConfig.initDatabaseConnection();
                    try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                            "Update genre Set " + attr + "=" + newValue + " WHERE genre_id=" + id)) {
                        statement.execute();
                        originalGenre = Optional.ofNullable(getGenreById(id));
                        return originalGenre;
                    }
                } catch (SQLException e) {
                    System.err.println(e);
                } catch (Error e) {
                    System.err.println(e.getMessage());
                }
            } else {
                throw new Error("Kein Genre in der Datenbank mit der ID: " + id);
            }
        } else {
            throw new Error("Bitte keine leeren Felder angeben");
        }
        return originalGenre;
    }

    public String deleteGenre(Long id) throws SQLException {
        Optional<Genre> originalGenre = Optional.ofNullable(getGenreById(id));
        if (originalGenre.isPresent()) {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "DELETE FROM genre WHERE genre_id =" + id)) {
                statement.execute();
                return "Genre erfolgreich gelöscht";
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                dbConfig.closeDatabaseConnection();
            }
        } else if (originalGenre.isEmpty()) {

            return "kein Genre mit der ID" + id + "vorhanden";

        }
        return "Löschen nicht möglich";
    }
}
