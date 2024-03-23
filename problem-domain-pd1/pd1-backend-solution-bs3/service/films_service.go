package service

import (
	"context"
	"database/sql"
	"pd1-backend-solution-bs3/openapi"
	"pd1-backend-solution-bs3/repo"
)

type FilmsAPIService struct{}

func NewFilmsAPIService() openapi.FilmsAPIServicer {
	return &FilmsAPIService{}
}

// CreateFilm - Creates a new film
func (s *FilmsAPIService) CreateFilm(ctx context.Context, film openapi.Film) (openapi.ImplResponse, error) {
	newFilm, err := repo.CreateFilm(film)
	if err != nil {
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}
	return openapi.Response(200, newFilm), nil
}

// DeleteFilm - Deletes a film by id
func (s *FilmsAPIService) DeleteFilm(ctx context.Context, id int32) (openapi.ImplResponse, error) {
	_, err := repo.GetFilm(int(id))
	if err != nil {
		if err == sql.ErrNoRows {
			return openapi.Response(404, openapi.Error{Title: "Error", Message: "Film not found"}), nil
		}
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}

	err = repo.DeleteFilm(int(id))
	if err != nil {
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}
	return openapi.Response(204, nil), nil
}

// GetFilm - Gets a film by id
func (s *FilmsAPIService) GetFilm(ctx context.Context, id int32) (openapi.ImplResponse, error) {
	film, err := repo.GetFilm(int(id))
	if err != nil {
		if err == sql.ErrNoRows {
			return openapi.Response(404, openapi.Error{Title: "Error", Message: "Film not found"}), nil
		}
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}
	return openapi.Response(200, film), nil
}

// GetFilmList - Gets a list of all films
func (s *FilmsAPIService) GetFilmList(ctx context.Context, limit int32, offset int32, sortBy string, order string) (openapi.ImplResponse, error) {
	filmList, err := repo.GetFilms(int(limit), int(offset), sortBy, order)
	if err != nil {
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}
	return openapi.Response(200, filmList), nil
}

// UpdateFilm - Updates an existing film
func (s *FilmsAPIService) UpdateFilm(ctx context.Context, film openapi.Film) (openapi.ImplResponse, error) {
	_, err := repo.GetFilm(int(film.FilmId))
	if err != nil {
		if err == sql.ErrNoRows {
			return openapi.Response(404, openapi.Error{Title: "Error", Message: "Film not found"}), nil
		}
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}

	updatedFilm, err := repo.UpdateFilm(film)
	if err != nil {
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), nil
	}
	return openapi.Response(200, updatedFilm), nil
}
