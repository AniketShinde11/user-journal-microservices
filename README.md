# User Journal Microservices

A production-ready microservices application built using Spring Boot and Spring Cloud. The application provides secure user management, journal management, email notifications, and service-to-service communication using modern microservices architecture.

---

## 🚀 Features

- User Registration & Login
- JWT Authentication & Authorization
- Journal Management
- Email Notifications
- API Gateway
- Service Discovery using Eureka
- Inter-service communication using OpenFeign
- Circuit Breaker & Retry using Resilience4j
- Global Exception Handling
- Input Validation
- Environment Variables Support
- Ready for Docker & Jenkins CI/CD

---

## 🛠 Tech Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 3.x |
| Spring Security | 6.x |
| Spring Cloud Gateway | Latest |
| Eureka Server | Latest |
| OpenFeign | Latest |
| Resilience4j | Latest |
| SQL Server | 2022 |
| Maven | 3.x |
| Git & GitHub | Latest |
| Docker | Planned |
| Jenkins | Planned |

---

## 🏗 Microservices Architecture

```text
Client
   |
   v
API Gateway (9090)
   |
   +--------------------+
   |                    |
   v                    v
User Service        Journal Service
   |
   v
Email Service

All services are registered with Eureka Server.
```

---

## 📂 Project Structure

```text
user-journal-microservices
│
├── api-gateway
├── service-registry
├── UserServiceApplication
├── JournalServiceApplication
├── EmailServiceApplication
└── README.md
```

---

## 🔐 Authentication Flow

1. User registers.
2. User logs in using username and password.
3. JWT token is generated.
4. Client sends JWT token in Authorization header.
5. API Gateway validates the token.
6. Request is forwarded to microservices.

---

## 📡 Services

### API Gateway
- Routing
- JWT Validation
- Central Entry Point

### Service Registry
- Eureka Service Discovery

### User Service
- User CRUD Operations
- Authentication
- Email Integration

### Journal Service
- Create Journal
- Update Journal
- Delete Journal
- Fetch User Journals

### Email Service
- Send Welcome Emails
- Asynchronous Email Processing

---

## ⚙️ Environment Variables

Create a `.env` file:

```env
DB_USER=sa
DB_PASSWORD=********
JWT_KEY=********
MAIL_USERNAME=********
MAIL_PASSWORD=********
```

---

## ▶️ Run Project

### Start Eureka Server

```bash
mvn spring-boot:run
```

### Start Services

```bash
mvn spring-boot:run
```

---

## 📷 Screenshots

You can add:

- Eureka Dashboard
- Swagger UI
- Login API
- Journal APIs
- Email Notification

---

## 🔮 Future Enhancements

- Docker Support
- Jenkins CI/CD Pipeline
- Kubernetes Deployment
- Redis Caching
- Kafka Integration
- Monitoring using Prometheus & Grafana
- Centralized Logging using ELK Stack

---

## 👨‍💻 Author

**Aniket Shinde**

Java Backend Developer

Skills:
Java | Spring Boot | Microservices | SQL Server | AWS | Jenkins | Docker | Git | CI/CD

GitHub:
https://github.com/AniketShinde11

LinkedIn:
(Add your LinkedIn profile here)

---
