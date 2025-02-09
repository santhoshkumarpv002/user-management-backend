# Use an official Eclipse Temurin runtime as a parent image
FROM eclipse-temurin:21

# Set the working directory in the container
WORKDIR /app
#

# Copy the Maven wrapper-related files to the container
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Make the Maven wrapper script executable
RUN chmod +x ./mvnw

# Install dependencies
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./mvnw
RUN ./mvnw dependency:resolve

# Copy the source code to the container
COPY src ./src

# Build the Spring Boot application
RUN ./mvnw package

# Set the name of the application jar file dynamically
ARG JAR_FILE=target/*.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
CMD ["sh", "-c", "java -jar ${JAR_FILE}"]
