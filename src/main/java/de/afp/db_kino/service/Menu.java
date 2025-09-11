package de.afp.db_kino.service;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public static String menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
        boolean exit = false;
        while (!exit) {
            System.out.println("Bitte option wählen");
            System.out.println("""
                     1: Film
                     2: Genre
                     3: Raum
                    -1: EXIT
                    """);
            option = scanner.nextInt();
            System.out.println(option);
            switch (option) {
                case 1:
                    exit = FilmMenu.film(scanner);
                    break;
                case -1:
                    exit = true;
                    break;
            }
        }

        return "Programm beendet";
    }
}
