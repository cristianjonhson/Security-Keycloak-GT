# Build stage
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache dependencies first for faster rebuilds
COPY pom.xml ./
RUN mvn -B -DskipTests dependency:go-offline

# Copy source and build the Spring Boot jar
COPY src ./src
RUN mvn -B clean package -DskipTests

# Runtime stage (distroless, smaller and safer by default)
FROM gcr.io/distroless/java17-debian12:nonroot
WORKDIR /app

# Copy built artifact
COPY --from=builder /app/target/api-gestion-academica-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
