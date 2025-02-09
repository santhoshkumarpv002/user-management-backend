# Use the official Maven image to build the app
# https://hub.docker.com/_/maven
FROM maven:3.6.3-openjdk-21 AS build
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Use the official OpenJDK image for running the app
# https://hub.docker.com/_/openjdk
FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
