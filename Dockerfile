
FROM openjdk:22-ea-1-slim

WORKDIR /app

COPY target/project-management-0.0.1-SNAPSHOT.jar tp01-pb.jar

CMD ["java", "-jar", "tp01-pb.jar"]

