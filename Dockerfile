# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy the project JAR file into the container
COPY target/musicFinder-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the app runs on
EXPOSE 8080
# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]