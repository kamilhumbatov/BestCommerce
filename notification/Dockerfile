FROM openjdk:14-jdk-alpine
VOLUME /tmp
ADD "target/notification.jar" app.jar
ENV JAVA_OPTS=""
# Fire up our Spring Boot app by default
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]