package eureka

import (
	"fmt"
	"strconv"

	"github.com/ArthurHlt/go-eureka-client/eureka"
)

func RegisterClient() {
	client := eureka.NewClient([]string{"http://localhost:8761/eureka"})

	instanceHost := "localhost"
	instancePort := 8082
	instanceAppName := "pd1-backend-solution-bs3"

	instance := eureka.NewInstanceInfo(
		instanceHost,
		instanceAppName,
		instanceHost,
		instancePort,
		30,
		false,
	)

	instance.HomePageUrl = fmt.Sprintf("http://%s:%s/", instanceHost, strconv.Itoa(instancePort))
	instance.InstanceID = fmt.Sprintf("%s:%s:%s", instanceHost, instanceAppName, strconv.Itoa(instancePort))
	instance.VipAddress = instanceAppName

	instance.Metadata = &eureka.MetaData{Map: make(map[string]string)}
	instance.Metadata.Map["problemDomainId"] = "pd1"
	instance.Metadata.Map["title"] = "Golang SQL Sakila Films CRUD"
	instance.Metadata.Map["summary"] = " Solution based on the Go programming language. Imperative approach with plain SQL."
	instance.Metadata.Map["tags"] = "Go, SQL, Mux"
	instance.Metadata.Map["sourceLink"] = "https://github.com/harisux/fullstack-playground-1/tree/main/problem-domain-pd1/pd1-backend-solution-bs3"
	instance.Metadata.Map["detailsList"] = "Language: Go | Approach: Imperative SQL | Key libraries: Eureka client, Gorilla Mux, Go SQL | Extra: OpenAPI Go Generator"

	client.RegisterInstance(instanceAppName, instance)

	fmt.Println("Heartbeat to instance...")
	client.SendHeartbeat(instance.App, instance.HostName)
	fmt.Println(client.GetInstance(instance.App, instance.HostName))
}
