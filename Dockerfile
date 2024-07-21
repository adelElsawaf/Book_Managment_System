FROM openjdk:21-oracle
ADD build/libs/Book-Managment-System-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java" ,"-jar" , "Book-Managment-System-0.0.1-SNAPSHOT.jar"]