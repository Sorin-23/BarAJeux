#FROM maven:3.9.11-eclipse-temurin-21 AS builder

#WORKDIR /app

#COPY pom.xml .

# Télécharger les dépendances Maven
# L'option -B permet d'exécuter la commande maven en mode Batch (pas d'interaction avec l'utilisateur)
#RUN mvn dependency:go-offline -B

#COPY ./src ./src

#RUN mvn package

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./target/*.jar projetJeux-boot.jar

CMD [ "java", "-jar", "projetJeux-boot.jar" ]

EXPOSE 8080