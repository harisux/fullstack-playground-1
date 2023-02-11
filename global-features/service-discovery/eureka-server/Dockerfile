FROM maven:3.8.7-amazoncorretto-17
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src ./src
RUN mvn clean install
ENTRYPOINT ["java", "-jar", "target/eureka-server-0.0.1-SNAPSHOT.jar"]