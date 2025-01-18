# Step 1: Use the official OpenJDK base image with Java 17
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the packaged JAR file from your host to the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the application port (default port for Spring Boot is 8090)
EXPOSE 8090

# Step 5: Run the JAR file using the command below
ENTRYPOINT ["java", "-jar", "app.jar"]