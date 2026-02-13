# task-manager-clean-architecture
Task Manager built with Clean Architecture, Java, Gradle, PostgreSQL.
his project serves both as a learning platform and as a professional portfolio-quality repository.

---

# 📐 Architecture Used — Clean Architecture

This project follows a **3-layer Clean Architecture structure**, fully separated and independent:

### **🔵 Domain Layer (Core – Pure Logic)**
Contains **no frameworks** and **no external dependencies**.
- Entities (`Task`, `User`, etc.)
- Use Cases (`CreateTask`, `CompleteTask`, `AssignTask`, etc.)
- Repository Interfaces (`TaskRepository`, `UserRepository`)

This layer defines *what* the system does.

---

### **🟢 Infrastructure Layer (Technical Implementations)**
Implements the interfaces defined in the domain.
- PostgreSQL repositories
- JDBC or JPA mappings
- External services (email, notifications, hashing, etc.)

This layer defines *how* the system works internally.

---

### **🟣 Presentation Layer (Entry Point)**
Responsible for communication with the user or external systems.
- REST controllers
- CLI interface (optional)
- DTOs & request/response models

This layer converts input/output into domain use cases.

---

# 🚀 Features

- Create, update and delete tasks
- Set priorities and deadlines
- Multiple task lists
- User authentication
- Share tasks between users
- Mark tasks as completed
- Filters by priority, status and due date
- Clean, scalable and decoupled architecture

---

# 🛠️ Tech Stack

| Component | Technology |
|----------|------------|
| Language | Java 21 |
| Build System | Gradle |
| Database | PostgreSQL |
| Architecture | Clean Architecture |
| Testing | JUnit + Mockito |
| Version Control | Git + GitHub |

---

# 📦 Installation & Execution

### **1️⃣ Clone the repository**
```bash
git clone https://github.com/USERNAME/task-manager-clean-architecture.git
cd task-manager-clean-architecture
