package repo

import (
	"database/sql"
	"log"

	"github.com/go-sql-driver/mysql"
)

var db *sql.DB

func ConnectToDb() {
	config := mysql.Config{
		User:   "sakilauser1",
		Passwd: "sakilauser1",
		Net:    "tcp",
		Addr:   "localhost:3306",
		DBName: "sakila",
	}

	var err error
	db, err = sql.Open("mysql", config.FormatDSN())
	if err != nil {
		log.Fatal(err)
	}

	pingErr := db.Ping()
	if pingErr != nil {
		log.Fatal(pingErr)
	}
	log.Println("Connected to DB")
}
