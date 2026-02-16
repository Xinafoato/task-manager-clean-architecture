# ⚙️ Task Manager Backend — Clean Architecture

![Java](https://img.shields.io/badge/Java-21-blue)
![Gradle](https://img.shields.io/badge/Build-Gradle-green)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-orange)
![Database](https://img.shields.io/badge/Database-PostgreSQL%20%7C%20SQLite-lightgrey)
![Testing](https://img.shields.io/badge/Testing-JUnit-red)

Backend service for the **Task Manager Clean Architecture** project.

This module implements the business logic following strict Clean Architecture principles, ensuring scalability, testability, and framework independence.

---

# 🚀 Overview

The backend provides a REST API that allows:

- Create task lists
- Retrieve task lists by user
- Rename task lists
- Delete task lists
- Persist data in a relational database

The architecture separates core business rules from infrastructure concerns.

---

# 🧠 Backend Architecture

```
backend
│
└── src
    └── main
        └── java
            └── com.marti
                ├── domain
                │   ├── model
                │   └── repository
                │
                ├── application
                │   └── usecase
                │
                ├── infrastructure
                │   └── persistence
                │
                └── presentation
                    └── controller
```

---

## 📌 Layer Responsibilities

### Domain
- Enterprise business rules
- Entities (TaskList, Task, User)
- Repository interfaces
- No framework dependencies

### Application
- Use cases
- Orchestrates domain logic
- Calls repository interfaces

### Infrastructure
- Database implementation
- Repository implementations
- External systems integration

### Presentation
- REST Controllers
- Request/Response mapping
- HTTP layer

---

# 🛠️ Tech Stack

| Component | Technology |
|------------|------------|
| Language | Java 21 |
| Build Tool | Gradle |
| Database | PostgreSQL / SQLite |
| API | REST |
| Testing | JUnit |

---

# ⚙️ Requirements

- Java 21+
- Gradle
- PostgreSQL (or SQLite if configured)

---

# 📦 Installation

From project root:

```bash
cd backend
```

Build project:

```bash
./gradlew build
```

---

# ▶️ Run the Application

```bash
./gradlew run
```

API will start on:

```
http://localhost:8080
```

---

# 🗄️ Database Configuration

Configure your database connection inside your infrastructure layer or via environment variables.

Example (PostgreSQL):

```
DB_URL=jdbc:postgresql://localhost:5432/taskdb
DB_USER=your_user
DB_PASS=your_password
```

Example (SQLite):

```
jdbc:sqlite:taskdb.db
```

---

# 🧪 Run Tests

```bash
./gradlew test
```

---

# 🔎 Example API Endpoints

### Get task lists by user

```
GET /tasklists?userId=user1
```

### Create task list

```
POST /tasklists
```

### Rename task list

```
PUT /tasklists/{id}
```

### Delete task list

```
DELETE /tasklists/{id}
```

---

# 🧱 Design Principles Applied

- Clean Architecture
- Dependency Inversion
- Single Responsibility Principle
- Separation of Concerns
- Framework Independence
- Testable Business Logic

---

# 📈 Why This Backend Is Portfolio-Ready

This backend demonstrates:

- Proper multi-layer architecture
- Clean separation between business and infrastructure
- Replaceable database layer
- Professional project structure
- Scalable design ready for extension

---

# 👨‍💻 Author

Martí Bessa  
Software Engineering Student  
Focused on scalable backend systems and Clean Architecture.
