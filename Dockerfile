# Imagem base com Java 25
FROM eclipse-temurin:25-jdk

WORKDIR /deployments

# Copia os arquivos do build fast-jar do Quarkus
COPY target/quarkus-app/lib/ /deployments/lib/
COPY target/quarkus-app/*.jar /deployments/
COPY target/quarkus-app/app/ /deployments/app/
COPY target/quarkus-app/quarkus/ /deployments/quarkus/

# Configura a porta 7000
EXPOSE 7000
ENV QUARKUS_HTTP_PORT=7000

# Comando para iniciar
CMD ["java", "-jar", "/deployments/quarkus-run.jar"]