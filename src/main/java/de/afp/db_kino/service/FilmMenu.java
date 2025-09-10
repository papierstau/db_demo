package de.afp.db_kino.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import de.afp.db_kino.model.Film;

public class FilmMenu {

    final static FilmService FILMSERVICE = new FilmService();

    public static boolean film(Scanner scanner) throws SQLException {

        System.out.println("""
                1: Filme anzeigen
                2: Film suchen über ID
                3: Film anlegen
                4: Film aktualisieren
                5: Film löschen
                6: exit
                """);
        String opt = scanner.next();
        scanner.nextLine();
        switch (opt) {
            case "1":
                System.out.println(FILMSERVICE.getFilms());
                Menu.menu();
                break;
            case "2":
                System.out.println("Bitte geben sie die FilmId ein");
                Long l = scanner.nextLong();
                System.out.println(FILMSERVICE.getFilmById(l));
                Menu.menu();
                break;
            case "3":
                Film film = new Film();
                System.out.println("Bitte den Titel angeben");
                String titel = scanner.nextLine();
                film.setTitel(titel);
                System.out.println("Bitte die Dauer in Minuten angeben");
                film.setDauer(scanner.nextInt());
                System.out.println("Bitte die FSK angeben");
                film.setFskFreigabe(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Bitte Zusammenfassung angebeben");
                film.setInhalt(scanner.nextLine());
                System.out.println("Bitte Datum im Format yyyy-MM-dd angeben");
                film.setErschungsdatum(LocalDate.parse(scanner.nextLine()));
                System.out.println(film.toString());
                System.out.println("""
                        	Wollen sie diesen Film in der Datenbank speichern?
                        	Drücken sie y für Ja
                        	oder
                        	eine beliebige Taste um den Vorgang abzubrechen
                        """);
                if (scanner.nextLine().equals("y")) {
                    FILMSERVICE.saveFilm(film);
                }
                Menu.menu();
                break;
            case "4":
                System.out.println("Bitte geben Sie einen Filmtitel ein");
                List<Film> f = FILMSERVICE.getFilmsByName(scanner.nextLine());
                System.out.println(f);
                System.out.println("Bitte geben sie eine ID an, welchen Film sie verändern möchten");
                Long id = scanner.nextLong();
                scanner.nextLine();
                System.out.println("Welches Feld soll verändert werden?");
                String attr = scanner.nextLine();
                // scanner.nextLine();
                System.out.println("Mit welchem Wert soll das Feld geupdatet werden?");
                String value = scanner.nextLine();
                FILMSERVICE.updateFilm(id, attr.toLowerCase(), value);
                Menu.menu();
                break;
            case "5":
                System.out.println("Bitte geben sie die Id des Films an, welcher gelöscht werden soll");
                Long filmId = scanner.nextLong();
                System.out.println(FILMSERVICE.deleteFilm(filmId));
                Menu.menu();
                break;
            case "6":
                Menu.menu();
                break;

        }

        return true;

    }
}
