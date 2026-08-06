FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY src/ src/
COPY lib/ lib/
RUN mkdir out && javac -cp "lib/*" -d out \
    src/model/*.java \
    src/database/*.java \
    src/parser/*.java \
    src/commands/*.java \
    src/dashboard/*.java \
    src/WebApp.java

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/out/ out/
COPY --from=build /app/lib/ lib/
EXPOSE 8080
CMD ["java", "-Xmx256m", "-cp", "out:lib/*", "WebApp"]
