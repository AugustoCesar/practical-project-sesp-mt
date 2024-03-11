FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/Practical-Project-0.0.1-SNAPSHOT.jar practical-project.jar
ENTRYPOINT ["java","-jar","/practical-project.jar"]
