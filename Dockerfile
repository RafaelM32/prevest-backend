## Estágio 1: Build (Compilação)
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . /usr/src/app
WORKDIR /usr/src/app
# Executa o build do Quarkus gerando o fast-jar
RUN mvn package -DskipTests

## Estágio 2: Run (Execução)
FROM registry.access.redhat.com/ubi8/openjdk-21:1.23
ENV LANGUAGE='en_US:en'

WORKDIR /
# Copiamos os arquivos gerados no estágio anterior (build)
COPY --from=build /usr/src/app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /usr/src/app/target/quarkus-app/*.jar /deployments/
COPY --from=build /usr/src/app/target/quarkus-app/app/ /deployments/app/
COPY --from=build /usr/src/app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]