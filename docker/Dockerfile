FROM java:8
VOLUME /tmp
COPY simbadder-0.2.0.jar app.jar
EXPOSE 7901
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "&>", "/dev/null"]
