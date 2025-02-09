FROM eclipse-temurin:21

WORKDIR /app
COPY ./mvn .mvn
COPY mvnw pom.xml ./

RUN chmod +x ./mvnw


RUN apt-get update && apt-get install -Y dos2unix
RUN dos2unix ./mvnw
RUN  ./mvnw dependency:resolve

COPY src ./src
CMD [ "./mvnw","spring-boot:run" ]