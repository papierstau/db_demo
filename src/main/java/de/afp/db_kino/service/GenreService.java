package de.afp.db_kino.service;

import java.lang.foreign.Linker.Option;
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
                    Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre"));
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
            try(PreparedStatement statement = dbConfig.connection.prepareStatement(
                "SELECT * FROM genre WHERE genre_id=" + id)) {
                    Optional<ResultSet> resultSet = Optional.ofNullable(statement.executeQuery());
                    if (resultSet.get().next()){
                        genre = new Genre (resultSet.get().getLong("genre_id"), resultSet.get().getString("genre".toString()));
                        return genre;   
                    }else {
                        throw new Error();
                    }
                }
        } catch (SQLException e) {
            System.err.println(e);
        }catch (Error e){
            System.err.println("Kein Genre mit der Id: " + id + "gefunden");
        }finally {
            dbConfig.closeDatabaseConnection();
        } 
        return null;
    }

}
