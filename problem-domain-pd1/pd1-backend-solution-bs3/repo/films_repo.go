package repo

import (
	"fmt"
	"pd1-backend-solution-bs3/openapi"
	"strings"
	"time"
)

func GetFilms(limit, offset int, sortBy, order string) (openapi.FilmList, error) {
	query := `
		select 
			F.film_id, F.title, F.description, F.release_year, F.language_id,
			L.name, F.rental_duration, F.rental_rate, F.length, F.replacement_cost, 
			F.rating, F.special_features
		from film F
		left join language L on F.language_id = L.language_id
	`
	sortField := sanitizeSortField(sortBy)

	//adding order by
	orderBy := "asc"
	if strings.ToLower(order) == "desc" {
		orderBy = "desc"
	}
	query = query + fmt.Sprintf(" order by %s %s", sortField, orderBy)

	//adding limit, offset
	query += " limit ?"
	var args []any

	if offset > 0 {
		query += ",?"
		args = []any{offset, limit}
	} else {
		args = []any{limit}
	}

	stmt, err := db.Prepare(query)
	if err != nil {
		return openapi.FilmList{}, err
	}

	rows, err := stmt.Query(args...)
	if err != nil {
		return openapi.FilmList{}, err
	}
	defer rows.Close()

	films := []openapi.Film{}
	totalCount := 0
	for rows.Next() {
		film := openapi.Film{}
		if err := rows.Scan(
			&film.FilmId, &film.Title, &film.Description, &film.ReleaseYear, &film.Language.LanguageId,
			&film.Language.Name, &film.RentalDuration, &film.RentalRate, &film.Length, &film.ReplacementCost,
			&film.Rating, &film.SpecialFeatures,
		); err != nil {
			return openapi.FilmList{}, err
		}
		films = append(films, film)
		totalCount++
	}

	if err := rows.Err(); err != nil {
		return openapi.FilmList{}, err
	}

	return openapi.FilmList{Data: films, TotalCount: float32(totalCount)}, nil
}

func GetFilm(filmId int) (openapi.Film, error) {
	query := `
		select 
			F.film_id, F.title, F.description, F.release_year, F.language_id,
			L.name, F.rental_duration, F.rental_rate, F.length, F.replacement_cost, 
			F.rating, F.special_features
		from film F
		left join language L on F.language_id = L.language_id 
		where film_id = ?
    `
	row := db.QueryRow(query, filmId)

	film := openapi.Film{}
	if err := row.Scan(
		&film.FilmId, &film.Title, &film.Description, &film.ReleaseYear, &film.Language.LanguageId,
		&film.Language.Name, &film.RentalDuration, &film.RentalRate, &film.Length, &film.ReplacementCost,
		&film.Rating, &film.SpecialFeatures,
	); err != nil {
		return openapi.Film{}, err
	}
	return film, nil
}

func CreateFilm(film openapi.Film) (openapi.Film, error) {
	query := `
		insert into film ( 
			title, description, release_year, language_id,
			original_language_id, rental_duration, rental_rate, length, 
			replacement_cost, rating, special_features, last_update
		) values (
			?,?,?,?,?,?,?,?,?,?,?,?
		)
    `

	res, err := db.Exec(query,
		film.Title, film.Description, film.ReleaseYear, film.Language.LanguageId,
		nil, film.RentalDuration, film.RentalRate, film.Length,
		film.ReplacementCost, film.Rating, film.SpecialFeatures, time.Now(),
	)
	if err != nil {
		return openapi.Film{}, err
	}

	newFilmId, err := res.LastInsertId()
	if err != nil {
		return openapi.Film{}, err
	}

	film.FilmId = int32(newFilmId)
	return film, nil
}

func sanitizeSortField(sortFieldIn string) string {
	sortField := "F.film_id"
	allowedOpts := []string{"title", "description", "release_year",
		"language_id", "rental_rate", "length", "replacement_cost",
		"rating", "special_features"}

	for _, opt := range allowedOpts {
		if opt == sortFieldIn {
			sortField = "F." + sortFieldIn
			break
		}
	}

	return sortField
}
