# Etapa 1: build com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o restante do código e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final só com JDK
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o .jar do build anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
