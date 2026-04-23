# Build stage
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy source and build the Spring Boot jar
COPY pom.xml ./
COPY src ./src
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built artifact
COPY --from=builder /app/target/api-gestion-academica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
