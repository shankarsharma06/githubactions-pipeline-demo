openlogic-openjdk-17.0.12+7-windows-x64

# Use an official Java runtime as a parent image
FROM openjdk:openlogic-openjdk-17.0.12+7-windows-x64

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Compile and package the application
RUN ./mvnw clean package

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "target/s3-email-service-1.0-SNAPSHOT.jar"]
