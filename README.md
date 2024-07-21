Overview

The Book Management System is a Java Spring Boot application that provides a comprehensive solution for managing books. It allows users to perform CRUD (Create, Read, Update, Delete) operations on book records, including details such as title, author, ISBN, and publish date.
Features

    Book Management: Add, update, delete, and view book details.
    Validation: Ensure data integrity with built-in validation rules.
    Exception Handling: Global exception handling for a smooth user experience.
    DTO Mapping: Use of Data Transfer Objects (DTOs) to manage data flow.
    Customizable: Easily configurable and extendable to meet additional needs.

Technologies Used

    Java 21: The programming language used.
    Spring Boot: Framework for building the application.
    Spring Data JPA : For database operations
    ModelMapper: For mapping between DTOs and entities.
    JUnit 5: For testing the application.
    MockMVC : For Integration Testing in controllers
    Gradle: Build automation tool.

Getting Started

Prerequisites

    Java JDK 21: Ensure that Java 21 is installed on your machine.
    Gradle: Installed and set up for managing dependencies and builds.

Clone the Repository
    
    git clone  git@github.com:adelElsawaf/Book_Managment_System.git
    cd Book-Managment-System

Build the project:

    note : if using intellij it will auto build the gradle file
            so no need for this step

    from terminal in project root run the following command 
    ./gradlew build

Run the project

The application will start and be accessible at http://localhost:8080.


To Run through Docker Image please follow these steps

Clone the Repository
    
    git clone  git@github.com:adelElsawaf/Book_Managment_System.git
    cd Book-Managment-System

Run the following in project root terminal

    ./gradlew clean
    ./gradlew build
    sh docker-bash-scripts/build_and_run.sh 

API Endpoints

    Create Book: POST /books/
    Get All Books: GET /books/
    Get Book by ID: GET /books/{id}
    Update Book: PUT /books/{id}
    Delete Book: DELETE /books/{id}    

