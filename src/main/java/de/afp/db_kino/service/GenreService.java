package de.afp.db_kino.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        }catch(Error e){}
                    return genre;
                }

}
