# ClickTrack

**A Link Tracking & Click Analytics REST API built with Spring MVC**

ClickTrack is a Java web application that enables publishers to create tracking links, monitor user clicks, and analyze the performance of their promotional offers through a RESTful API.

The project focuses on clean layered architecture, dependency injection, REST API design, and in-memory data management using Spring Framework and XML-based configuration.

## Features

- **Publisher Management:** Retrieve publishers and their information.
- **Tracking Link Creation:** Create promotional tracking links associated with publishers.
- **Click Tracking:** Record clicks on tracking links and retrieve their destination URLs.
- **Offer Analytics:** Aggregate click statistics and link counts by offer.
- **RESTful API:** Expose endpoints using HTTP methods and appropriate status codes.
- **Layered Architecture:** Separate controllers, services, repositories, models, and DTOs.
- **Dependency Injection:** Inject dependencies through interfaces using Spring XML configuration.
- **Thread-Safe Click Counting:** Synchronize click counter updates within the repository.
- **Initial Data Seeding:** Populate the application with sample data through a data initializer.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Spring Framework | Dependency injection and application infrastructure |
| Spring MVC | REST API and request handling |
| Maven | Dependency management and build automation |
| Apache Tomcat 10.1 | Servlet container and deployment |
| XML | Spring bean and web application configuration |
| Docker | Containerized deployment |
| JUnit | Unit testing |

## Architecture

ClickTrack follows a layered architecture to separate responsibilities and improve maintainability.

```text
HTTP Client
    |
    v
Controller Layer
    |
    v
Service Layer
    |
    v
Repository Interface
    |
    v
Repository Implementation
    |
    v
In-Memory Storage (ArrayList)
```

### Layer Responsibilities

- **Controller:** Handles HTTP requests, validates request data, and returns HTTP responses.
- **Service:** Implements business rules and coordinates application operations.
- **Repository:** Manages data access and storage operations.
- **Model:** Represents the application's core domain entities.
- **DTO:** Defines the structure of request and response payloads.
- **Spring Configuration:** Registers beans and wires dependencies through XML files.

The application uses interface-based dependency injection to reduce coupling between layers and facilitate testing.

## Domain Models

### Publisher

Represents a content creator or publisher who manages tracking links.

- `id`
- `firstname`
- `lastname`
- `email`
- `category`

Supported categories:

- `INFLUENCER`
- `YOUTUBER`
- `BLOGUER`

### Link

Represents a promotional tracking link associated with a publisher.

- `id`
- `offre`
- `urlDestination`
- `clicks`
- `publisher`

## REST API

Base URL:

```text
http://localhost:8081/clicktrack
```

### 1. Retrieve All Publishers

`GET /api/publishers`

Returns the list of registered publishers.

**Response:** `200 OK`

### 2. Create a Tracking Link

`POST /api/publishers/{id}/liens`

Creates a tracking link for the specified publisher.

**Request body:**

```json
{
  "offre": "Premium",
  "urlDestination": "https://example.com/premium"
}
```

**Responses:**
- `201 Created` when the link is created successfully.
- `404 Not Found` when the publisher does not exist.

### 3. Retrieve Publisher Links

`GET /api/publishers/{id}/liens`

Returns the links associated with a publisher, including their click counts.

**Responses:**
- `200 OK`
- `404 Not Found` when the publisher does not exist.

### 4. Retrieve Offer Statistics

`GET /api/publishers/{id}/offres`

Returns aggregated statistics for the publisher's offers, including the number of links and total clicks.

**Example response:**

```json
[
  {
    "offre": "Premium",
    "nombreliens": 2,
    "clicks": 11
  }
]
```

**Response:** `200 OK`

### 5. Track a Click

`GET /t/{id}`

Increments the click counter for the specified tracking link and returns tracking information.

**Example:** `GET /t/1`

**Example response:**

```json
{
  "urlRedirection": "https://example.com/premium",
  "publisher": "Mohamed El Masghati",
  "offre": "Premium",
  "clicks": 5
}
```

**Responses:**
- `200 OK` when the tracking link exists.
- `404 Not Found` when the tracking link does not exist.

> Note: The current tracking endpoint returns JSON containing the destination URL. Automatic HTTP redirection is a separate enhancement.

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── ma/clicktrack/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── model/
│   │       ├── repository/
│   │       │   └── implementation/
│   │       └── service/
│   │           └── implementation/
│   ├── resources/
│   │   └── applicationContext.xml
│   └── webapp/
│       └── WEB-INF/
│           ├── web.xml
│           └── dispatcher-servlet.xml
└── test/
    └── java/
```

## Configuration

ClickTrack uses XML-based Spring configuration.

- `applicationContext.xml`: Registers repositories, services, and the data initializer.
- `dispatcher-servlet.xml`: Configures Spring MVC, component scanning for controllers, and annotation-driven request mapping.
- `web.xml`: Defines the web application's servlet configuration and application context initialization.

Controllers use annotations such as `@RestController`, `@GetMapping`, and `@PostMapping` for request mapping, while application beans are explicitly registered in XML.

## Getting Started

### Prerequisites

Make sure the following tools are installed:

- JDK 17
- Apache Maven
- Docker Desktop, if using containerized deployment

### 1. Clone the Repository

```bash
git clone <YOUR_REPOSITORY_URL>
cd clicktrack
```

Replace `<YOUR_REPOSITORY_URL>` with the actual Git repository URL.

### 2. Build the Application

```bash
mvn clean package
```

This command compiles the source code, runs the configured tests, and generates the WAR artifact.

### 3. Deploy with Docker

Build the Docker image:

```bash
docker build -t clicktrack-app:1.0 .
```

Start the container:

```bash
docker run -d --name clicktrack-tomcat -p 8081:8080 clicktrack-app:1.0
```

The API will be accessible at:

```text
http://localhost:8081/clicktrack
```

Ensure that the Docker image deploys the generated WAR under the `clicktrack` context path.

### 4. Test the API

You can use Postman, PowerShell, or another HTTP client to test the endpoints.

Retrieve publishers:

```http
GET http://localhost:8081/clicktrack/api/publishers
```

Create a tracking link:

```http
POST http://localhost:8081/clicktrack/api/publishers/1/liens
Content-Type: application/json
```

```json
{
  "offre": "Premium",
  "urlDestination": "https://example.com/premium"
}
```

Track a click:

```http
GET http://localhost:8081/clicktrack/t/1
```

## Testing

Run the automated test suite with:

```bash
mvn test
```

The project includes unit tests for repository operations and service-layer business logic.

Concurrency testing can also be performed by sending multiple simultaneous requests to the tracking endpoint and comparing the number of successful requests with the change in the click counter.

## Current Limitations

- Data is stored in memory using `ArrayList`; application data is not persistent across application restarts.
- Click counting is synchronized within the repository, but concurrency guarantees for all repository operations require separate verification.
- Automatic HTTP redirection is not implemented in the current tracking endpoint.
- Spring ORM integration is an optional enhancement and is not part of the current in-memory persistence implementation.

## Future Improvements

- Integrate Spring ORM with a relational database.
- Implement persistent storage for publishers and tracking links.
- Add automatic HTTP redirection after tracking a click.
- Strengthen concurrency guarantees for link creation, ID generation, and repository operations.
- Add API documentation using OpenAPI/Swagger.
- Introduce integration tests and automated API testing.
- Add authentication, authorization, and request validation.

## Learning Objectives

This project provides practical experience with:

- Spring Core and Inversion of Control (IoC).
- Dependency injection and programming to interfaces.
- Layered architecture and separation of concerns.
- Spring MVC and REST API development.
- Maven-based Java web application builds.
- Unit testing and concurrent request handling.
- Docker-based deployment.

## Author

**Mohamed El Masghati**

Java Developer | Spring Framework | REST API Development

GitHub: [elmasghatimohamed-cmd](https://github.com/elmasghatimohamed-cmd)

---

*ClickTrack — Build links, track clicks, measure performance.*