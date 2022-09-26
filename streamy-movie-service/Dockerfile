FROM openjdk:8
EXPOSE 8084 5672
ADD target/movie-service-1.0.jar streamy-movieservice.jar
ENTRYPOINT [ "java", "-jar" , "/streamy-movieservice.jar" ]