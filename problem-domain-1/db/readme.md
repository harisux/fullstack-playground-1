## Build the image 
docker build -t mysqlsakilaimg1 .

## Run the image
docker run -d -p 3306:3306 --name mysqlsakilacntr1 mysqlsakilaimg1
