# Loan Management System (LMS)

This project implements a Loan Management System that integrates with a Bank's Core Banking System (CBS) and a Scoring Engine to provide micro-loan products to bank customers.

## System Architecture

The system consists of the following components:

1. **LMS Core Service**: Handles loan applications, status checks, and subscription management
2. **Transaction Data Service**: Provides transaction data to the Scoring Engine
3. **Integration Layer**: Connects to external services (CBS SOAP APIs and Scoring Engine REST APIs)

![Architecture Diagram](https://via.placeholder.com/800x400)

## Features

- Customer subscription management
- Loan application processing
- Loan status tracking
- Integration with SOAP-based KYC and Transaction Data APIs
- Integration with REST-based Scoring Engine
- Retry mechanism for score queries

## API Documentation

The system exposes the following RESTful APIs:

1. **Subscription API**: Register a customer for loan services
2. **Loan Request API**: Submit a loan application
3. **Loan Status API**: Check the status of an existing loan application
4. **Transaction Data API**: Provide transaction data to the Scoring Engine (internal)

Detailed API documentation is available through Swagger UI at `http://localhost:8080/swagger-ui.html` when the application is running.

## Prerequisites

- Java 17+
- Maven
- Docker and Docker Compose

## Building the Application

```bash
# Clone the repository
git clone https://github.com/alifeofbytes/credable.git
cd loan-management-system

# Build with Maven
mvn clean package
```

## Running the Application

### Using Docker Compose

```bash
# Start all services
docker-compose up -d
```

### Running Locally

```bash
# Start the application
java -jar target/loan-management-system.jar
```

## Configuration

The application can be configured through the `application.yml` file:

```yaml
server:
  port: 8080

spring:
  application:
    name: loan-management-system

# CBS Integration Settings
cbs:
  kyc:
    url: https://kycapitest.credable.io/service/customerWsdl.wsdl
    username: admin
    password: pwd123
  transaction:
    url: https://trxapitest.credable.io/service/transactionWsdl.wsdl
    username: admin
    password: pwd123

# Scoring Engine Settings
scoring:
  base-url: https://scoringtest.credable.io/api/v1
  retry:
    max-attempts: 5
    delay-ms: 5000
```

## Testing

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### Testing with Sample Data

The following customer IDs can be used for testing:
- 234774784
- 318411216
- 340397370
- 366585630
- 397178638

## Project Structure

```
loan-management-system/
├── src/
│   ├── main/
│   │   ├── java/io/credable/lms/
│   │   │   ├── api/
│   │   │   ├── config/
│   │   │   ├── domain/
│   │   │   ├── exception/
│   │   │   ├── integration/
│   │   │   ├── service/
│   │   │   └── util/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Development Workflow

1. Clone the repository
2. Build the application
3. Run the application with Docker Compose
4. Access the API documentation at `http://localhost:8080/swagger-ui.html`
5. Use the provided test data to interact with the API

## License

This project is licensed under the MIT License - see the LICENSE file for details.