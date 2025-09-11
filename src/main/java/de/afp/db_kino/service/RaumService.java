package de.afp.db_kino.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.afp.db_kino.config.DBConfig;
import de.afp.db_kino.model.Raum;

public class RaumService {

    private DBConfig dbConfig = new DBConfig();
    private List<Raum> raeume = new ArrayList<>();

    public List<Raum> getRaeume() throws SQLException {
        raeume.clear();
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement("""
                    	SELECT *
                    	FROM raum
                    """)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Raum raum = new Raum(resultSet.getInt("raum_id"), resultSet.getString("name"),
                            resultSet.getBoolean("3d"), resultSet.getInt("kapazitaet"));
                    raeume.add(raum);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
        return raeume;
    }

    // Testen was passiert, wenn man nach einer ID sucht die es nicht gibt?
    // Optional ResultSet eventuell noch mit einer IF-Abfrage nutzen
    public Raum getRaumById(int id) throws SQLException {
        raeume.clear();
        Raum raum = new Raum();
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "SELECT * FROM raum WHERE raum_id =" + id)) {
                Optional<ResultSet> resultSet = Optional.ofNullable(statement.executeQuery());
                if (resultSet.get().next()) {
                    raum = new Raum(resultSet.get().getInt("raum_id"), resultSet.get().getString("name"),
                            resultSet.get().getBoolean("3d"), resultSet.get().getInt("kapazitaet"));
                    return raum;
                } else {
                    throw new Error();
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        } catch (Error e) {
            System.err.println("Kein Raum mit der Id: " + id + " gefunden");
        } finally {
            dbConfig.closeDatabaseConnection();
        }
        return null;
    }
    
    public List<Raum> getRaeumeByName(String name) throws SQLException {
        raeume.clear();
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection
                    .prepareStatement("SELECT * FROM raum WHERE name LIKE \"%" + name + "%\"")) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Raum raum = new Raum(resultSet.getInt("raum_id"), resultSet.getString("name"),
                            resultSet.getBoolean("3d"), resultSet.getInt("kapazitaet"));
                    raeume.add(raum);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
        return raeume;
    }

    public void saveRaum(Raum raum) throws SQLException {
        try {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "INSERT INTO raum(name, 3d, kapazitaet) VALUES(\""
                            + raum.getName() + "\","
                            + raum.getIs_3d() + ","
                            + raum.getKapazitaet()
                            + ")")) {

                statement.execute();
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            dbConfig.closeDatabaseConnection();
        }
    }

    public Optional<Raum> updateRaum(int id, String attr, String value) throws SQLException {
        Optional<Raum> originalRaum = Optional.ofNullable(getRaumById(id));
        Object newValue = new Object();
        if (!value.isBlank() && !attr.isBlank()) {
            if (originalRaum.isPresent()) {
                switch (attr) {
                    case "name" -> newValue = "\"".concat(value).concat("\"");
                    case "3d" -> newValue = Boolean.parseBoolean(value);
                    case "kapazitaet" -> newValue = Integer.parseInt(value);
                    default -> throw new Error("kein gueltiges Attribut");
                }
                try {
                    dbConfig.initDatabaseConnection();
                    try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                            "Update raum Set " + attr + "=" + newValue + " WHERE raum_id=" + id)) {
                        statement.execute();
                        originalRaum = Optional.ofNullable(getRaumById(id));
                        System.out.println("Der entsprechende Wert wurde in der Datenbank aktualisiert!");
                        return originalRaum;
                    }
                } catch (SQLException e) {
                    System.err.println(e);
                } catch (Error e) {
                    System.err.println(e.getMessage());
                }
            } else {
                throw new Error("Kein Raum in der Datenbank mit der ID: " + id);
            }
        } else {
            throw new Error("Bitte keine leeren Felder angeben!");
        }
        return originalRaum;
    }

    public String deleteRaum(int id) throws SQLException {
        Optional<Raum> originalRaum = Optional.ofNullable(getRaumById(id));
        if (originalRaum.isPresent()) {
            dbConfig.initDatabaseConnection();
            try (PreparedStatement statement = dbConfig.connection.prepareStatement(
                    "DELETE FROM raum WHERE raum_id =" + id)) {
                statement.execute();
                return "Raum wurde erfolgreich gelöscht";
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                dbConfig.closeDatabaseConnection();
            }
        } else if (originalRaum.isEmpty()) {

            return "kein Raum mit der ID " + id + " vorhanden";

        }
        return "Löschen nicht möglich";
    }

}
