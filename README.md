# Blog Application Setup Guide

## 1. Project Scaffolding
- **Spring Initializr** - [Spring Initializr](https://start.spring.io/)
  - **Gradle - Groovy**: Package name = `peep.pea.blog`
  - **Java**: Version 21, Packaging = jar
  - **Spring Boot**: Version 3.3.0

## 2. Dependencies for Spring Initializr
Ensure to include the following dependencies when setting up your project via Spring Initializr:
- **Spring Web**: For building web applications.
- **Spring Data JPA**: For database integration using Java Persistence API.
- **Spring Security**: For authentication and authorization.
- **PostgreSQL Driver**: To connect to PostgreSQL database.
- **H2 Database**: For in-memory database during development and testing.
- **Lombok**: To reduce boilerplate code like getters, setters.

---

## 3. Domain Model
- **Entities**: Define Java classes that map to database tables. Typical entities include `Post`, `Comment`, and `User`.
- **Repositories**: Utilize Spring Data JPA to automatically implement interfaces for database interactions.

---

## 4. Business Logic
- **Services**: Encapsulate business logic related to posts and comments within service classes. These handle transactions and ensure adherence to business rules.
- **DTOs**: Define Data Transfer Objects to safely transfer data between the API layer and the client, safeguarding sensitive information.

---

## 5. API Layer
- **REST Controllers**: Manage HTTP requests and responses, interfacing with service classes to process business logic.
- **Exception Handling**: Employ `@ControllerAdvice` to globally manage exceptions and provide cleaner error responses.

---

## 6. Security Configuration
- **JWT for Authentication**: Configure Spring Security to utilize JSON Web Tokens (JWTs) for stateless authentication.
- **Authorization**: Implement role-based access control to manage user access to resources based on their roles.

---

## About Database Usage
- **JPA (Java Persistence API)**: Manages data interaction between Java objects and relational databases, with Spring Data JPA providing an abstraction layer that simplifies data manipulation.
- **JDBC (Java Database Connectivity)**: The underlying technology for database interactions, managed by JPA implementations like Hibernate.
- **H2 Database**: Used for testing purposes due to its in-memory capabilities.
- **PostgreSQL Driver**: Connects your application to the PostgreSQL database.
- **Lombok**: Simplifies Java code by automatically generating getters, setters, and other common code.


---

## peep.pea.blog.user

### Overview
The `user` package in the application manages user information, including registration, profile updates, and authentication.

### Components

- **User.java**: Represents the user entity with fields such as:
  1. username
  2. password
  3. email
  4. role
  5. last online timestamp
  6. profile picture URL
  This record supports the straightforward transfer of data within the application.

- **UserController.java**: The UserController handles CRUD operations on users. It includes methods for:
  1. Retrieving a single user by ID.
  2. Creating a new user.
  3. Listing all users with pagination.
  The controller uses `Pageable` and `UriComponentsBuilder`, which are best practices for RESTful APIs.

- **UserService.java**: The UserService manages the business logic associated with user operations. It provides methods to:
  1. Create a user.
  2. Retrieve a user by ID.
  3. Update user details.
  4. Delete a user.
  5. Fetch all users with pagination.
  This service ensures that operations like updating user details respect the immutability of the user record by creating a new record.

- **UserRepository.java**: Extends both `CrudRepository` and `PagingAndSortingRepository`, providing comprehensive CRUD operations and pagination capabilities. It includes methods to:
  1. Find users by ID and username.
  2. Save and delete users.
  3. Check for the existence of a username.
  This repository is integral to the data handling layer, directly interacting with the database.
