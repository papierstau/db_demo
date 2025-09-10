package de.afp.db_kino;

import java.sql.SQLException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.afp.db_kino.service.FilmService;
import de.afp.db_kino.service.Menu;

@SpringBootApplication
public class DbKinoApplication {
	final static FilmService FILMSERVICE = new FilmService();

	public static void main(String[] args) throws SQLException {

		Menu.menu();
		// SpringApplication.run(DbKinoApplication.class, args);

	}

}
