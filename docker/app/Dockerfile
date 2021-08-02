FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=app.jar
ADD ${JAR_FILE} /app.jar
ENV JAVA_OPTS=""
ENV TZ "America/Los_Angeles"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]