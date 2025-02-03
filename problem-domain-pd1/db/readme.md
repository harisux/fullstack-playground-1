## Docker commands

### Build the image 
`docker build -t mysql-sakila-img .`

### Run the image
`docker run -d -p <host-port-here>:3306 --name mysql-sakila mysql-sakila-img`
