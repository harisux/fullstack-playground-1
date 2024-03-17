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
	"time"
	"github.com/gorilla/mux"
	"io"
	"mime/multipart"
	"net/http"
	"net/url"
	"os"
	"strconv"
	"strings"
)

// A Route defines the parameters for an api endpoint
type Route struct {
	Method	  string
	Pattern	 string
	HandlerFunc http.HandlerFunc
}

// Routes is a map of defined api endpoints
type Routes map[string]Route

// Router defines the required methods for retrieving api routes
type Router interface {
	Routes() Routes
}

const errMsgRequiredMissing = "required parameter is missing"
const errMsgMinValueConstraint = "provided parameter is not respecting minimum value constraint"
const errMsgMaxValueConstraint = "provided parameter is not respecting maximum value constraint"

// NewRouter creates a new router for any number of api routers
func NewRouter(routers ...Router) *mux.Router {
	router := mux.NewRouter().StrictSlash(true)
	for _, api := range routers {
		for name, route := range api.Routes() {
			var handler http.Handler
			handler = route.HandlerFunc
			handler = Logger(handler, name)

			router.
				Methods(route.Method).
				Path(route.Pattern).
				Name(name).
				Handler(handler)
		}
	}

	return router
}

// EncodeJSONResponse uses the json encoder to write an interface to the http response with an optional status code
func EncodeJSONResponse(i interface{}, status *int, w http.ResponseWriter) error {
	wHeader := w.Header()

	f, ok := i.(*os.File)
	if ok {
		data, err := io.ReadAll(f)
		if err != nil {
			return err
		}
		wHeader.Set("Content-Type", http.DetectContentType(data))
		wHeader.Set("Content-Disposition", "attachment; filename="+f.Name())
		if status != nil {
			w.WriteHeader(*status)
		} else {
			w.WriteHeader(http.StatusOK)
		}
		_, err = w.Write(data)
		return err
	}
	wHeader.Set("Content-Type", "application/json; charset=UTF-8")

	if status != nil {
		w.WriteHeader(*status)
	} else {
		w.WriteHeader(http.StatusOK)
	}

	if i != nil {
		return json.NewEncoder(w).Encode(i)
	}

	return nil
}

// ReadFormFileToTempFile reads file data from a request form and writes it to a temporary file
func ReadFormFileToTempFile(r *http.Request, key string) (*os.File, error) {
	_, fileHeader, err := r.FormFile(key)
	if err != nil {
		return nil, err
	}

	return readFileHeaderToTempFile(fileHeader)
}

// ReadFormFilesToTempFiles reads files array data from a request form and writes it to a temporary files
func ReadFormFilesToTempFiles(r *http.Request, key string) ([]*os.File, error) {
	if err := r.ParseMultipartForm(32 << 20); err != nil {
		return nil, err
	}

	files := make([]*os.File, 0, len(r.MultipartForm.File[key]))

	for _, fileHeader := range r.MultipartForm.File[key] {
		file, err := readFileHeaderToTempFile(fileHeader)
		if err != nil {
			return nil, err
		}

		files = append(files, file)
	}

	return files, nil
}

// readFileHeaderToTempFile reads multipart.FileHeader and writes it to a temporary file
func readFileHeaderToTempFile(fileHeader *multipart.FileHeader) (*os.File, error) {
	formFile, err := fileHeader.Open()
	if err != nil {
		return nil, err
	}

	defer formFile.Close()

	// Use .* as suffix, because the asterisk is a placeholder for the random value,
	// and the period allows consumers of this file to remove the suffix to obtain the original file name
	file, err := os.CreateTemp("", fileHeader.Filename+".*")
	if err != nil {
		return nil, err
	}

	defer file.Close()

	_, err = io.Copy(file, formFile)
	if err != nil {
		return nil, err
	}
	
	return file, nil
}

func parseTimes(param string) ([]time.Time, error) {
	splits := strings.Split(param, ",")
	times := make([]time.Time, 0, len(splits))
	for _, v := range splits {
		t, err := parseTime(v)
		if err != nil {
			return nil, err
		}
		times = append(times, t)
	}
	return times, nil
}

// parseTime will parses a string parameter into a time.Time using the RFC3339 format
func parseTime(param string) (time.Time, error) {
	if param == "" {
		return time.Time{}, nil
	}
	return time.Parse(time.RFC3339, param)
}

type Number interface {
	~int32 | ~int64 | ~float32 | ~float64
}

type ParseString[T Number | string | bool] func(v string) (T, error)

// parseFloat64 parses a string parameter to an float64.
func parseFloat64(param string) (float64, error) {
	if param == "" {
		return 0, nil
	}

	return strconv.ParseFloat(param, 64)
}

// parseFloat32 parses a string parameter to an float32.
func parseFloat32(param string) (float32, error) {
	if param == "" {
		return 0, nil
	}

	v, err := strconv.ParseFloat(param, 32)
	return float32(v), err
}

// parseInt64 parses a string parameter to an int64.
func parseInt64(param string) (int64, error) {
	if param == "" {
		return 0, nil
	}

	return strconv.ParseInt(param, 10, 64)
}

// parseInt32 parses a string parameter to an int32.
func parseInt32(param string) (int32, error) {
	if param == "" {
		return 0, nil
	}

	val, err := strconv.ParseInt(param, 10, 32)
	return int32(val), err
}

// parseBool parses a string parameter to an bool.
func parseBool(param string) (bool, error) {
	if param == "" {
		return false, nil
	}

	return strconv.ParseBool(param)
}

type Operation[T Number | string | bool] func(actual string) (T, bool, error)

func WithRequire[T Number | string | bool](parse ParseString[T]) Operation[T] {
	var empty T
	return func(actual string) (T, bool, error) {
		if actual == "" {
			return empty, false, errors.New(errMsgRequiredMissing)
		}

		v, err := parse(actual)
		return v, false, err
	}
}

func WithDefaultOrParse[T Number | string | bool](def T, parse ParseString[T]) Operation[T] {
	return func(actual string) (T, bool, error) {
		if actual == "" {
			return def, true, nil
		}

		v, err := parse(actual)
		return v, false, err
	}
}

func WithParse[T Number | string | bool](parse ParseString[T]) Operation[T] {
	return func(actual string) (T, bool, error) {
		v, err := parse(actual)
		return v, false, err
	}
}

type Constraint[T Number | string | bool] func(actual T) error

func WithMinimum[T Number](expected T) Constraint[T] {
	return func(actual T) error {
		if actual < expected {
			return errors.New(errMsgMinValueConstraint)
		}

		return nil
	}
}

func WithMaximum[T Number](expected T) Constraint[T] {
	return func(actual T) error {
		if actual > expected {
			return errors.New(errMsgMaxValueConstraint)
		}

		return nil
	}
}

// parseNumericParameter parses a numeric parameter to its respective type.
func parseNumericParameter[T Number](param string, fn Operation[T], checks ...Constraint[T]) (T, error) {
	v, ok, err := fn(param)
	if err != nil {
		return 0, err
	}

	if !ok {
		for _, check := range checks {
			if err := check(v); err != nil {
				return 0, err
			}
		}
	}

	return v, nil
}

// parseBoolParameter parses a string parameter to a bool
func parseBoolParameter(param string, fn Operation[bool]) (bool, error) {
	v, _, err := fn(param)
	return v, err
}

// parseNumericArrayParameter parses a string parameter containing array of values to its respective type.
func parseNumericArrayParameter[T Number](param, delim string, required bool, fn Operation[T], checks ...Constraint[T]) ([]T, error) {
	if param == "" {
		if required {
			return nil, errors.New(errMsgRequiredMissing)
		}

		return nil, nil
	}

	str := strings.Split(param, delim)
	values := make([]T, len(str))

	for i, s := range str {
		v, ok, err := fn(s)
		if err != nil {
			return nil, err
		}

		if !ok {
			for _, check := range checks {
				if err := check(v); err != nil {
					return nil, err
				}
			}
		}

		values[i] = v
	}

	return values, nil
}


// parseQuery parses query paramaters and returns an error if any malformed value pairs are encountered.
func parseQuery(rawQuery string) (url.Values, error) {
	return url.ParseQuery(rawQuery)
}