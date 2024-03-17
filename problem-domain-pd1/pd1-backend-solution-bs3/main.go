package main

import (
	"log"
	"net/http"
	"pd1-backend-solution-bs3/openapi"
	"pd1-backend-solution-bs3/service"
)

func main() {
	log.Printf("Server started")

	filmsAPIService := service.NewFilmsAPIService()
	filmsAPIController := openapi.NewFilmsAPIController(filmsAPIService)

	router := openapi.NewRouter(filmsAPIController)

	log.Fatal(http.ListenAndServe(":8082", router))
}
