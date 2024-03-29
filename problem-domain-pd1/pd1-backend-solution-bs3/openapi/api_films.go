/*
 * Sakila Films CRUD API
 *
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * API version: 1.0
 * Generated by: OpenAPI Generator (https://openapi-generator.tech)
 */

package openapi

import (
	"encoding/json"
	"errors"
	"io"
	"net/http"
	"strings"

	"github.com/gorilla/mux"
)

// FilmsAPIController binds http requests to an api service and writes the service results to the http response
type FilmsAPIController struct {
	service      FilmsAPIServicer
	errorHandler ErrorHandler
}

// FilmsAPIOption for how the controller is set up.
type FilmsAPIOption func(*FilmsAPIController)

// WithFilmsAPIErrorHandler inject ErrorHandler into controller
func WithFilmsAPIErrorHandler(h ErrorHandler) FilmsAPIOption {
	return func(c *FilmsAPIController) {
		c.errorHandler = h
	}
}

// NewFilmsAPIController creates a default api controller
func NewFilmsAPIController(s FilmsAPIServicer, opts ...FilmsAPIOption) Router {
	controller := &FilmsAPIController{
		service:      s,
		errorHandler: DefaultErrorHandler,
	}

	for _, opt := range opts {
		opt(controller)
	}

	return controller
}

// Routes returns all the api routes for the FilmsAPIController
func (c *FilmsAPIController) Routes() Routes {
	return Routes{
		"CreateFilm": Route{
			strings.ToUpper("Post"),
			"/api/v1/films",
			c.CreateFilm,
		},
		"DeleteFilm": Route{
			strings.ToUpper("Delete"),
			"/api/v1/films/{id}",
			c.DeleteFilm,
		},
		"GetFilm": Route{
			strings.ToUpper("Get"),
			"/api/v1/films/{id}",
			c.GetFilm,
		},
		"GetFilmList": Route{
			strings.ToUpper("Get"),
			"/api/v1/films",
			c.GetFilmList,
		},
		"UpdateFilm": Route{
			strings.ToUpper("Put"),
			"/api/v1/films",
			c.UpdateFilm,
		},
	}
}

// CreateFilm - Creates a new film
func (c *FilmsAPIController) CreateFilm(w http.ResponseWriter, r *http.Request) {
	filmParam := Film{}
	d := json.NewDecoder(r.Body)
	d.DisallowUnknownFields()
	if err := d.Decode(&filmParam); err != nil && !errors.Is(err, io.EOF) {
		c.errorHandler(w, r, &ParsingError{Err: err}, nil)
		return
	}
	if err := AssertFilmRequired(filmParam); err != nil {
		c.errorHandler(w, r, err, nil)
		return
	}
	if err := AssertFilmConstraints(filmParam); err != nil {
		c.errorHandler(w, r, err, nil)
		return
	}
	result, err := c.service.CreateFilm(r.Context(), filmParam)
	// If an error occurred, encode the error with the status code
	if err != nil {
		c.errorHandler(w, r, err, &result)
		return
	}
	// If no error, encode the body and the result code
	EncodeJSONResponse(result.Body, &result.Code, w)
}

// DeleteFilm - Deletes a film by id
func (c *FilmsAPIController) DeleteFilm(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	idParam, err := parseNumericParameter[int32](
		params["id"],
		WithRequire[int32](parseInt32),
	)
	if err != nil {
		c.errorHandler(w, r, &ParsingError{Err: err}, nil)
		return
	}
	result, err := c.service.DeleteFilm(r.Context(), idParam)
	// If an error occurred, encode the error with the status code
	if err != nil {
		c.errorHandler(w, r, err, &result)
		return
	}
	// If no error, encode the body and the result code
	EncodeJSONResponse(result.Body, &result.Code, w)
}

// GetFilm - Gets a film by id
func (c *FilmsAPIController) GetFilm(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	idParam, err := parseNumericParameter[int32](
		params["id"],
		WithRequire[int32](parseInt32),
	)
	if err != nil {
		c.errorHandler(w, r, &ParsingError{Err: err}, nil)
		return
	}
	result, err := c.service.GetFilm(r.Context(), idParam)
	// If an error occurred, encode the error with the status code
	if err != nil {
		c.errorHandler(w, r, err, &result)
		return
	}
	// If no error, encode the body and the result code
	EncodeJSONResponse(result.Body, &result.Code, w)
}

// GetFilmList - Gets a list of all films
func (c *FilmsAPIController) GetFilmList(w http.ResponseWriter, r *http.Request) {
	query, err := parseQuery(r.URL.RawQuery)
	if err != nil {
		c.errorHandler(w, r, &ParsingError{Err: err}, nil)
		return
	}
	var limitParam int32
	if query.Has("limit") {
		param, err := parseNumericParameter[int32](
			query.Get("limit"),
			WithParse[int32](parseInt32),
			WithMinimum[int32](1),
			WithMaximum[int32](100),
		)
		if err != nil {
			c.errorHandler(w, r, &ParsingError{Err: err}, nil)
			return
		}

		limitParam = param
	} else {
		c.errorHandler(w, r, &RequiredError{Field: "limit"}, nil)
		return
	}
	var offsetParam int32
	if query.Has("offset") {
		param, err := parseNumericParameter[int32](
			query.Get("offset"),
			WithParse[int32](parseInt32),
			WithMinimum[int32](0),
		)
		if err != nil {
			c.errorHandler(w, r, &ParsingError{Err: err}, nil)
			return
		}

		offsetParam = param
	} else {
		var param int32 = 0
		offsetParam = param
	}
	var sortByParam string
	if query.Has("sort_by") {
		param := query.Get("sort_by")

		sortByParam = param
	} else {
		param := "film_id"
		sortByParam = param
	}
	var orderParam string
	if query.Has("order") {
		param := query.Get("order")

		orderParam = param
	} else {
		param := "asc"
		orderParam = param
	}
	result, err := c.service.GetFilmList(r.Context(), limitParam, offsetParam, sortByParam, orderParam)
	// If an error occurred, encode the error with the status code
	if err != nil {
		c.errorHandler(w, r, err, &result)
		return
	}
	// If no error, encode the body and the result code
	EncodeJSONResponse(result.Body, &result.Code, w)
}

// UpdateFilm - Updates an existing film
func (c *FilmsAPIController) UpdateFilm(w http.ResponseWriter, r *http.Request) {
	filmParam := Film{}
	d := json.NewDecoder(r.Body)
	d.DisallowUnknownFields()
	if err := d.Decode(&filmParam); err != nil && !errors.Is(err, io.EOF) {
		c.errorHandler(w, r, &ParsingError{Err: err}, nil)
		return
	}
	if err := AssertFilmRequired(filmParam); err != nil {
		c.errorHandler(w, r, err, nil)
		return
	}
	if err := AssertFilmConstraints(filmParam); err != nil {
		c.errorHandler(w, r, err, nil)
		return
	}
	result, err := c.service.UpdateFilm(r.Context(), filmParam)
	// If an error occurred, encode the error with the status code
	if err != nil {
		c.errorHandler(w, r, err, &result)
		return
	}
	// If no error, encode the body and the result code
	EncodeJSONResponse(result.Body, &result.Code, w)
}
