package repo

import "pd1-backend-solution-bs3/openapi"

func GetFilms() (openapi.FilmList, error) {
	rows, err := db.Query(`
		select 
			F.film_id, F.title, F.description, F.release_year, F.language_id,
			L.name, F.rental_duration, F.rental_rate, F.length, F.replacement_cost, 
			F.rating, F.special_features
		from film F
		left join language L on F.language_id = L.language_id
	`)
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
