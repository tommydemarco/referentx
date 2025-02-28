# ReferentX

ReferentX is a Spring Boot application for a Reference Management Software.
It was created for the course DLBCSPSE01.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java 17+
- Maven 3+
- MySQL Server
- Git

## Getting Started

### 1. Clone this Repository

### 2. Configure the Database

Create a MySQL database named `referentx` and update the `src/main/resources/application.properties` file with the correct database configurations (username, password, etc.).

Ensure MySQL is running and that the credentials in `src/main/resources/application.properties` match your MySQL setup.

You just need to create a database. The schema will be created automatically by the application once it starts and connects to the database, thanks to the `spring.jpa.hibernate.ddl-auto=update` configuration in the `src/main/resources/application.properties` file.

### 3. Build and Run the Application

You can do this conveniently using IntelliJ IDEA.

### 4. Access the Application

Once the application starts successfully, access it via:

```
http://localhost:8080
```
