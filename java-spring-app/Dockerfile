FROM openjdk:17-jdk-slim

WORKDIR /app

# Install Maven and curl
RUN apt-get update && apt-get install -y \
    maven \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "target/persian-tts-1.0.0.jar"]
