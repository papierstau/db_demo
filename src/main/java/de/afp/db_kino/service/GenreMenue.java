package de.afp.db_kino.service;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Scanner;

import de.afp.db_kino.model.Genre;

public class GenreMenue {
    final static GenreService GENRESERVICE = new GenreService();

    public static boolean genre(Scanner scanner) throws SQLException {
        System.out.println("""
                1: Genre anzeigen
                2: Genre suchen über ID
                3: Genre anlegen
                4: Genre aktualisieren
                5: Genre löschen
                6: Exit
                    """);
        String option = scanner.next();
        scanner.nextLine();
        switch (option) {
            case "1":
                System.out.println(GENRESERVICE.getGenres());
                Menu.menu();
                break;
            case "2":
                System.out.println("Bitte geben Sie die GenreId");
                Long l = scanner.nextLong();
                System.out.println(GENRESERVICE.getGenreById(l));
                Menu.menu();
                break;
            
            case "3":
                Genre genre = new Genre();
                System.out.println("Bitte das Genre angeben");
                String genreName = scanner.nextLine();
                genre.setGenreName(genreName);
                System.out.println(genre.toString());
                System.out.println("""
                        	Wollen sie diesen Film in der Datenbank speichern?
                        	Drücken sie y für Ja
                        	oder
                        	eine beliebige Taste um den Vorgang abzubrechen
                        """);
                if (scanner.nextLine().equals("y")) {
                    GENRESERVICE.saveGenre(genre);
                }
                Menu.menu();
                break;
                
    }

    return false;
}}
