FROM openjdk:8
EXPOSE 8082
ADD target/streamy-apigateway-0.0.1-SNAPSHOT.jar streamy-apigateway.jar
ENTRYPOINT [ "java", "-jar" , "/streamy-apigateway.jar" ]