# Etapa 1: Compilación con Maven y Java 21
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar el pom.xml y descargar dependencias
COPY pom.xml .
COPY src ./src

# Compilar el proyecto empaquetando el archivo JAR ejecutable
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Etapa 2: Entorno de ejecución ligero con JRE 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto mediante la variable de entorno $PORT
ENV PORT=8116
EXPOSE 8116

# Ejecutar la aplicación Spring Boot mapeando el puerto de Render
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8116} -jar app.jar"]
