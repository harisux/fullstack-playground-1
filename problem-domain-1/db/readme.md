## Docker commands

### Build the image 
`docker build -t mysql-sakila-img .`

### Run the image
`docker run -d -p 3306:3306 --name mysql-sakila mysql-sakila-img`
