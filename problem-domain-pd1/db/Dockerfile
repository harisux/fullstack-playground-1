#Initial mysql image
FROM mysql:latest

#Mysql settings
ENV MYSQL_ROOT_PASSWORD=secret
ENV MYSQL_DATABASE=sakila
ENV MYSQL_USER=sakilauser1
ENV MYSQL_PASSWORD=sakilauser1

#Copying init sql files
COPY sakila-schema.sql /tmp
COPY sakila-data.sql /tmp
RUN mv /tmp/sakila-schema.sql /docker-entrypoint-initdb.d/1-schema.sql
RUN mv /tmp/sakila-data.sql /docker-entrypoint-initdb.d/2-data.sql

#Running port
EXPOSE 3306