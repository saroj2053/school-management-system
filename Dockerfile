# 1. Base image with Java 21
FROM eclipse-temurin:21-jdk

# 2. Set working directory inside the container
WORKDIR /app

# 3. Copy the JAR file (replace with your actual JAR name)
COPY target/bright-future-academy-app.jar app.jar

EXPOSE 8080

# 6. Run the JAR
CMD ["java", "-jar", "app.jar"]
