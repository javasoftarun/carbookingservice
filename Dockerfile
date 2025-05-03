# Use a JDK image to build the application
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /CarAndBookingService
WORKDIR /CarAndBookingService
RUN gradle build --no-daemon

# Use a smaller JRE image to run the application
FROM eclipse-temurin:17-jre
EXPOSE 8082
COPY --from=build /CarAndBookingService/build/libs/CarAndBookingService-0.0.1-SNAPSHOT.jar CarAndBookingService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","CarAndBookingService-0.0.1-SNAPSHOT.jar"]
