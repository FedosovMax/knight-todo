FROM openjdk:11.0.4-jre-slim

WORKDIR /usr/src/myapp

COPY target/knight-todo-*.jar /knight.jar

CMD ["java", "-jar", "/knight.jar"]