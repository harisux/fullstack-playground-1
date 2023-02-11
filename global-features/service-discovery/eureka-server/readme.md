## Docker commands

### Build the image 
`docker build -t eureka-server-img .`

### Run the image
`docker run -d -p 8761:8761 --name eureka-server eureka-server-img`
