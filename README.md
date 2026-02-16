# 🗂️ Task Manager — Clean Architecture

![Java](https://img.shields.io/badge/Java-21-blue)
![Gradle](https://img.shields.io/badge/Build-Gradle-green)
![React](https://img.shields.io/badge/Frontend-React-61DAFB)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-orange)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A full-stack Task Manager application built using Clean Architecture principles.

This repository is designed as a portfolio-ready project demonstrating professional backend and frontend structure, scalability, and maintainability.

---

# 🚀 Project Overview

Task Manager allows users to:

- Create task lists
- Add tasks
- Rename task lists
- Delete task lists
- Associate task lists with users

The project follows strict Clean Architecture layering to ensure:

- Independence of frameworks
- Testable business logic
- Replaceable database
- Scalable structure

---

# 🧠 Architecture

```
task-manager-clean-architecture
│
├── backend
│   ├── domain
│   ├── application
│   ├── infrastructure
│   └── presentation
│
├── frontend
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Backend Layers

- domain → Entities and core business rules
- application → Use cases
- infrastructure → Database, persistence
- presentation → Controllers / REST API

## Frontend Structure

- pages → Screens
- components → Reusable UI components
- api → HTTP communication
- models → TypeScript interfaces

---

# 🛠️ Tech Stack

| Layer       | Technology |
|------------|------------|
| Backend     | Java 21 |
| Build Tool  | Gradle |
| Database    | PostgreSQL / SQLite |
| Frontend    | React + TypeScript |
| HTTP        | REST API |
| Testing     | JUnit |

---

# ⚙️ Installation

## 1️⃣ Clone the repository

```bash
git clone https://github.com/Xinafoato/task-manager-clean-architecture.git
cd task-manager-clean-architecture
```

---

# 🖥️ Backend Setup

## Requirements

- Java 21+
- PostgreSQL (or SQLite if configured)

## Run backend

```bash
cd backend
./gradlew build
./gradlew run
```

The API will start on:

```
http://localhost:8080
```

---

# 🌐 Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on:

```
http://localhost:5173
```

---

# 🧪 Running Tests

Backend:

```bash
./gradlew test
```

---

# 📈 Why This Project Stands Out

This project demonstrates:

- Proper layering
- Separation of concerns
- Dependency inversion
- Clean code principles
- Professional Git structure
- Scalable architecture

---

# 👨‍💻 Author

Martí Bessa

Software Engineering Student  
Focused on Clean Architecture and scalable backend systems.

---

# 📄 License

This project is licensed under the MIT License.
