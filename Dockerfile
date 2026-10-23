FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-25
ENV TZ="Europe/Oslo"
COPY target/eessi-pensjon-documentation-api.jar /app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0"
ENTRYPOINT ["java", "-jar", "/app.jar"]
