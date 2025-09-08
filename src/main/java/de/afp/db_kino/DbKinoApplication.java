package de.afp.db_kino;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.afp.db_kino.model.Film;
import de.afp.db_kino.service.FilmService;

@SpringBootApplication
public class DbKinoApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(DbKinoApplication.class, args);

		final FilmService FILMSERVICE = new FilmService();
		List<Film> filme = FILMSERVICE.getFilms();
		for (Film f : filme) {
			System.out.println(f.toString());
		}

		Film film = new Film("Matrix", 136, 16, "Hallo", LocalDate.parse("1999-03-31"));
		System.out.println(film.toString());
		// FILMSERVICE.saveFilm(film);
	}

}
