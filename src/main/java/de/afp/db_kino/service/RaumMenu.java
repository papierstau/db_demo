package de.afp.db_kino.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import de.afp.db_kino.model.Raum;

public class RaumMenu {
    
    final static RaumService RAUMSERVICE = new RaumService();

    public static void raumMenu(Scanner scanner) throws SQLException {
        System.out.println("""
                1. Räume anzeigen
                2. Raum suchen über den Namen
                3. Raum anlegen
                4. Raumwerte aktualisieren
                5. Raum löschen
                6. exit
                """);
        int opt = scanner.nextInt();
        scanner.nextLine();

        switch(opt) {
            case 1:
                System.out.println(RAUMSERVICE.getRaeume());
                raumMenu(scanner);
                break;
            case 2:
                System.out.println("Bitte geben Sie den Namen ein: ");
                System.out.println(RAUMSERVICE.getRaeumeByName(scanner.nextLine()));
                raumMenu(scanner);
            case 3:
                Raum raum = new Raum();
                System.out.println("Bitte geben Sie dem Raum einen Namen: ");
                raum.setName(scanner.nextLine());
                System.out.println("Kann der Raum 3D Filme zeigen? (Ja / Nein)");
                String kann_3d = scanner.nextLine();
                Boolean b_3d;
                if (kann_3d.toLowerCase().equals("ja")) {
                    b_3d = true;
                } else {
                    b_3d = false;
                }
                raum.setIs_3d(b_3d);
                System.out.println("Geben Sie nun die Kapazität des Raumes an: ");
                raum.setKapazitaet(scanner.nextInt());
                scanner.nextLine();

                System.out.println(raum.toString());
                System.out.println("""
                        Wollen Sie diesen Raum in der Datenbank speichern?
                        Drücken sie 'y' für Ja
                        oder
                        eine beliebige Taste um den Vorgang abzubrechen
                        """);
                if (scanner.nextLine().equals("y")) {
                    RAUMSERVICE.saveRaum(raum);
                    System.out.println(raum.getName() + " wurde in der Datenbank gespeichert!");
                } else {
                    System.out.println("Raum wurde nicht gespeichert!");
                }
                raumMenu(scanner);
                break;
            case 4:
                System.out.println("Geben Sie den Raumnamen ein, den Sie suchen: ");
                List<Raum> r = RAUMSERVICE.getRaeumeByName(scanner.nextLine());
                System.out.println(r); // For Schleife ums schöner zu machen?
                System.out.println("Bitte geben Sie die Raum-ID an, in der Sie Werte verändern möchten: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Welches Feld soll verändert werden? (name, 3d, kapazitaet)");
                String attr = scanner.nextLine();
                String value = "";
                switch (attr) {
                    case "name":
                        System.out.println("Geben Sie den neuen Namen an: ");
                        value = scanner.nextLine();
                        break;
                    case "3d":
                        System.out.println("Geben Sie an ob der Raum 3D-Fähig ist: (Ja/Nein)");
                        value = scanner.nextLine();
                        if (value.toLowerCase().equals("ja")) {
                            value = "true";
                        } else if (value.toLowerCase().equals("nein")) {
                            value = "false";
                        } else {
                            System.out.println("Ungültige Eingabe!");
                            raumMenu(scanner);
                        }
                        break;
                    case "kapazitaet":
                        System.out.println("Geben Sie die neue Kapaizität des Raums ein: ");
                        Integer zahl = scanner.nextInt();
                        value = zahl.toString();
                        break;
                    default:
                        System.out.println("Ungültiges Feld!");
                        raumMenu(scanner);
                }
                RAUMSERVICE.updateRaum(id, attr.toLowerCase(), value);
                raumMenu(scanner);
                break;
            case 5:
                System.out.println("Geben Sie die Raum-ID an, die gelöscht werden soll: ");
                int raumId = scanner.nextInt();
                System.out.println(RAUMSERVICE.deleteRaum(raumId));
                raumMenu(scanner);
                break;
            case 6:
                Menu.menu();
                break;
            default: 
                System.out.println("Ungültige Eingabe!!!");
                raumMenu(scanner);
        }
    }

}
