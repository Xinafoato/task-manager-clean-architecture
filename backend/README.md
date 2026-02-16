# ⚙️ Task Manager Backend — Clean Architecture

![Java](https://img.shields.io/badge/Java-21-blue)
![Gradle](https://img.shields.io/badge/Build-Gradle-green)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-orange)
![Testing](https://img.shields.io/badge/Testing-JUnit-red)

Backend service for the **Task Manager Clean Architecture** project.

This module implements the business logic following strict Clean Architecture principles, ensuring scalability, testability, and separation of concerns.

---

# 🚀 Overview

The backend provides a REST API and application logic that allows:

- User authentication
- Create task lists
- Manage tasks
- Rename and delete task lists
- Persist data in database
- Console-based interaction (CLI layer)

The system is designed to be independent from frameworks and external technologies.

---

# 🧠 Real Project Architecture

```
backend
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.marti
│   │   │       │
│   │   │       ├── application
│   │   │       │   ├── dtos
│   │   │       │   │   ├── auth
│   │   │       │   │   ├── task
│   │   │       │   │   └── taskList
│   │   │       │   │
│   │   │       │   ├── mappers
│   │   │       │   │
│   │   │       │   └── usecases
│   │   │       │       ├── auth
│   │   │       │       ├── task
│   │   │       │       └── taskList
│   │   │       │
│   │   │       ├── console
│   │   │       │   └── ui
│   │   │       │
│   │   │       ├── domain
│   │   │       │   ├── model
│   │   │       │   ├── repository
│   │   │       │   └── service
│   │   │       │
│   │   │       └── infrastructure
│   │   │           ├── persistence
│   │   │           └── server
│   │   │
│   │   └── resources
│   │
│   └── test
│       └── java
│           ├── persistence
│           └── usecases
│
├── build.gradle.kts
└── settings.gradle.kts
```

---

# 🧩 Layer Explanation

## 🟢 Domain Layer (Core Business)

```
domain
├── model
├── repository
└── service
```

- Contains enterprise business rules
- Entities like TaskList, Task, User
- Repository interfaces
- Domain services
- No external framework dependencies

This layer is completely independent.

---

## 🔵 Application Layer (Use Cases)

```
application
├── dtos
├── mappers
└── usecases
```

### dtos
Data Transfer Objects separated by feature:
- auth
- task
- taskList

### mappers
Converts:
- Entity ↔ DTO

### usecases
Organized by feature:
- auth
- task
- taskList

Each use case orchestrates domain logic and repository interaction.

---

## 🟡 Infrastructure Layer

```
infrastructure
├── persistence
└── server
```

### persistence
- Database implementations
- Repository implementations
- Data access logic

### server
- HTTP server
- REST configuration
- External integrations

---

## 🟣 Console Layer

```
console
└── ui
```

- Command-line interface
- Alternative presentation layer
- Demonstrates separation of UI from core logic

---

# 🧪 Testing Structure

```
test/java
├── persistence
└── usecases
```

- Unit tests for use cases
- Tests for persistence implementations
- Ensures business logic correctness

Run tests with:

```bash
./gradlew test
```

---

# 🛠️ Tech Stack

| Component | Technology |
|------------|------------|
| Language | Java 21 |
| Build Tool | Gradle |
| Database | PostgreSQL / SQLite |
| Architecture | Clean Architecture |
| Testing | JUnit |

---

# ⚙️ Installation

From project root:

```bash
cd backend
```

Build project:

```bash
./gradlew build
```

---

# ▶️ Run Application

```bash
./gradlew run
```

The API will start on:

```
http://localhost:8080
```

---

# 🗄️ Database Configuration

Configure database connection in infrastructure layer.

Example PostgreSQL:

```
DB_URL=jdbc:postgresql://localhost:5432/taskdb
DB_USER=your_user
DB_PASS=your_password
```

Example SQLite:

```
jdbc:sqlite:taskdb.db
```

---

# 🧱 Architectural Principles Applied

- Clean Architecture
- Dependency Inversion Principle
- Single Responsibility Principle
- Separation of Concerns
- Feature-based use case organization
- Independent business core

---

# 📈 Why This Backend Is Strong

This backend demonstrates:

- Real multi-layer architecture
- Feature-based use case separation
- DTO and Mapper pattern
- Repository abstraction
- Testable business logic
- Replaceable infrastructure
- CLI + HTTP presentation layers

It is structured to resemble a production-grade backend system.

---

# 👨‍💻 Author

Martí Bessa  
Software Engineering Student  
Focused on scalable backend systems and clean architecture.
