FROM docker.io/library/maven:3-eclipse-temurin-17 as build
WORKDIR /app
COPY pom.xml ./
COPY src src
RUN mvn clean verify \
  && mkdir target/jar \
  && cd target/jar \
  && jar -xf ../*.jar

FROM docker.io/library/eclipse-temurin:17
WORKDIR /app
COPY --from=build --chown=root:root /app/target/jar/BOOT-INF/lib lib
COPY --from=build --chown=root:root /app/target/jar/BOOT-INF/classes classes

ENTRYPOINT ["java", "-cp", "classes:lib/*", "kz.diploma.kitaphub.KitaphubApplication"]

EXPOSE 8080
