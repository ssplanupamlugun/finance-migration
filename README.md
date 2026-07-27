# Finance Migration

Finance Migration is a Spring Boot application used to migrate finance master data from Excel files to the Finance application through REST APIs. It provides a simple web interface built with Thymeleaf for uploading Excel files and processing migration requests.

## Features

* Upload Excel files through a web interface
* Read and process Excel using Apache POI
* Validate uploaded data
* Call Finance APIs using Spring WebClient
* Server-side rendering with Thymeleaf
* DTO mapping using MapStruct
* Java 21 and Spring Boot 4

---

# Technology Stack

* Java 21
* Spring Boot 4.1.0
* Postgres 12
* Spring MVC
* Thymeleaf
* Spring Validation
* Spring WebClient
* Apache POI
* MapStruct
* Lombok
* Maven
* Docker

---

# Project Structure

```
finance-migration
├── src
│   ├── main
│   │   ├── java
│   │   ├── resources
│   │   │   ├── templates
│   │   │   ├── static
│   │   │   └── application.yml
│   │   └── ...
│   └── test
├── Dockerfile
├── compose.yaml
├── pom.xml
└── README.md
```

---

# Prerequisites

Before running the project, ensure you have installed:

* Java 21
* Maven 3.9+
* Docker (optional)

---

# Clone Repository

```bash
git clone <repository-url>
cd finance-migration
```

---

# Build Project

```bash
mvn clean install
```

---

# Run Locally

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/finance-migration-0.0.1-SNAPSHOT.jar
```

The application will start on:

```
http://localhost:8080
```

---

# Running with Docker

## Build Docker Image

```bash
docker build -t finance-migration .
```

## Run Container

```bash
docker run -p 8080:8080 finance-migration
```

Application:

```
http://localhost:8080
```

---

# Running with Docker Compose

Start the application:

```bash
docker compose up --build
```

Run in background:

```bash
docker compose up -d --build
```

Stop containers:

```bash
docker compose down
```

---

# Configuration

Configure the application in:

```
src/main/resources/application.yml
```

---

# Maven Dependencies

Major dependencies used:

* Spring Boot Starter Web MVC
* Spring Boot Thymeleaf
* Spring Boot Starter Data JPA
* Spring Validation
* Spring WebClient
* Apache POI
* MapStruct
* Lombok
* Postgres

---

# Application Flow

1. Open the web application.
2. Upload the required Excel file.
3. Validate the uploaded data.
4. Transform Excel rows into request DTOs.
5. Send requests to Finance APIs.
6. Display migration results.

---

# Build Commands

Compile project

```bash
mvn compile
```

Run tests

```bash
mvn test
```

Package application

```bash
mvn package
```

Clean project

```bash
mvn clean
```

---

# Docker Commands

Build image

```bash
docker build -t finance-migration .
```

Run image

```bash
docker run -p 8080:8080 finance-migration
```

View logs

```bash
docker logs finance-migration
```

Stop container

```bash
docker stop finance-migration
```

---

# Future Enhancements

* Authentication support
* Progress tracking
* Batch migration
* Migration history
* Retry mechanism
* Error report download
* Dashboard for migration statistics

---

# License

This project is intended for internal use.
