package service

import (
	"context"
	"errors"
	"net/http"
	"pd1-backend-solution-bs3/openapi"
	"pd1-backend-solution-bs3/repo"
)

type FilmsAPIService struct{}

func NewFilmsAPIService() openapi.FilmsAPIServicer {
	return &FilmsAPIService{}
}

// CreateFilm - Creates a new film
func (s *FilmsAPIService) CreateFilm(ctx context.Context, film openapi.Film) (openapi.ImplResponse, error) {
	// TODO - update CreateFilm with the required logic for this service method.
	// Add api_films_service.go to the .openapi-generator-ignore to avoid overwriting this service implementation when updating open api generation.

	// TODO: Uncomment the next line to return response Response(201, Film{}) or use other options such as http.Ok ...
	// return Response(201, Film{}), nil

	// TODO: Uncomment the next line to return response Response(0, Error{}) or use other options such as http.Ok ...
	// return Response(0, Error{}), nil

	return openapi.Response(http.StatusNotImplemented, nil), errors.New("CreateFilm method not implemented")
}

// DeleteFilm - Deletes a film by id
func (s *FilmsAPIService) DeleteFilm(ctx context.Context, id int32) (openapi.ImplResponse, error) {
	// TODO - update DeleteFilm with the required logic for this service method.
	// Add api_films_service.go to the .openapi-generator-ignore to avoid overwriting this service implementation when updating open api generation.

	// TODO: Uncomment the next line to return response Response(204, {}) or use other options such as http.Ok ...
	// return Response(204, nil),nil

	// TODO: Uncomment the next line to return response Response(404, Error{}) or use other options such as http.Ok ...
	// return Response(404, Error{}), nil

	// TODO: Uncomment the next line to return response Response(0, Error{}) or use other options such as http.Ok ...
	// return Response(0, Error{}), nil

	return openapi.Response(http.StatusNotImplemented, nil), errors.New("DeleteFilm method not implemented")
}

// GetFilm - Gets a film by id
func (s *FilmsAPIService) GetFilm(ctx context.Context, id int32) (openapi.ImplResponse, error) {
	// TODO - update GetFilm with the required logic for this service method.
	// Add api_films_service.go to the .openapi-generator-ignore to avoid overwriting this service implementation when updating open api generation.

	// TODO: Uncomment the next line to return response Response(200, Film{}) or use other options such as http.Ok ...
	// return Response(200, Film{}), nil

	// TODO: Uncomment the next line to return response Response(404, Error{}) or use other options such as http.Ok ...
	// return Response(404, Error{}), nil

	// TODO: Uncomment the next line to return response Response(0, Error{}) or use other options such as http.Ok ...
	// return Response(0, Error{}), nil

	return openapi.Response(http.StatusNotImplemented, nil), errors.New("GetFilm method not implemented")
}

// GetFilmList - Gets a list of all films
func (s *FilmsAPIService) GetFilmList(ctx context.Context, limit int32, offset int32, sortBy string, order string) (openapi.ImplResponse, error) {
	// TODO - update GetFilmList with the required logic for this service method.
	// Add api_films_service.go to the .openapi-generator-ignore to avoid overwriting this service implementation when updating open api generation.

	// TODO: Uncomment the next line to return response Response(200, FilmList{}) or use other options such as http.Ok ...
	filmList, err := repo.GetFilms(int(limit), int(offset), sortBy, order)
	if err != nil {
		return openapi.Response(500, openapi.Error{Title: "Error", Message: "Internal Error"}), err
	}
	return openapi.Response(200, filmList), nil

	// TODO: Uncomment the next line to return response Response(0, Error{}) or use other options such as http.Ok ...
	// return Response(0, Error{}), nil

	//return openapi.Response(http.StatusNotImplemented, nil), errors.New("GetFilmList method not implemented")
}

// UpdateFilm - Updates an existing film
func (s *FilmsAPIService) UpdateFilm(ctx context.Context, film openapi.Film) (openapi.ImplResponse, error) {
	// TODO - update UpdateFilm with the required logic for this service method.
	// Add api_films_service.go to the .openapi-generator-ignore to avoid overwriting this service implementation when updating open api generation.

	// TODO: Uncomment the next line to return response Response(200, Film{}) or use other options such as http.Ok ...
	// return Response(200, Film{}), nil

	// TODO: Uncomment the next line to return response Response(409, Error{}) or use other options such as http.Ok ...
	// return Response(409, Error{}), nil

	// TODO: Uncomment the next line to return response Response(0, Error{}) or use other options such as http.Ok ...
	// return Response(0, Error{}), nil

	return openapi.Response(http.StatusNotImplemented, nil), errors.New("UpdateFilm method not implemented")
}
