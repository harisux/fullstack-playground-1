package main

import (
	"log"
	"net/http"
	"pd1-backend-solution-bs3/eureka"
	"pd1-backend-solution-bs3/openapi"
	"pd1-backend-solution-bs3/repo"
	"pd1-backend-solution-bs3/service"

	"github.com/gorilla/handlers"
)

func startRestServer() {
	log.Printf("Rest Server started")

	filmsAPIService := service.NewFilmsAPIService()
	filmsAPIController := openapi.NewFilmsAPIController(filmsAPIService)

	router := openapi.NewRouter(filmsAPIController)

	//Cors config
	origins := handlers.AllowedOrigins([]string{"*"})
	headers := handlers.AllowedHeaders([]string{"Content-Type"})
	methods := handlers.AllowedMethods([]string{"GET", "PUT", "POST", "DELETE"})

	log.Fatal(http.ListenAndServe(":8082", handlers.CORS(origins, headers, methods)(router)))
}

func main() {
	repo.ConnectToDb()
	eureka.RegisterClient()
	startRestServer()
}
