# 🎨 Task Manager Frontend

![React](https://img.shields.io/badge/React-18-61DAFB)
![TypeScript](https://img.shields.io/badge/TypeScript-Strict-blue)
![Vite](https://img.shields.io/badge/Bundler-Vite-purple)
![Architecture](https://img.shields.io/badge/Pattern-Clean%20Architecture-orange)

Frontend client for the Task Manager Clean Architecture project.

This application consumes the backend REST API and provides a clean and modern interface to manage task lists.

---

# 🚀 Features

- Create task lists
- Rename task lists
- Delete task lists
- Connect to backend REST API
- Clean and organized structure

---

# 🧠 Frontend Architecture

```
frontend
│
└── src
    ├── pages
    ├── components
    ├── api
    ├── models
    └── main.tsx
```

## Folder Responsibility

- pages → Application screens
- components → Reusable UI components
- api → HTTP requests and backend communication
- models → TypeScript interfaces and types

Clear separation between UI and API logic.

---

# ⚙️ Requirements

- Node 18+
- npm
- Backend running on port 8080

---

# 📦 Installation

```bash
cd frontend
npm install
```

---

# ▶️ Run Development Server

```bash
npm run dev
```

Application runs on:

```
http://localhost:5173
```

---

# 🔗 API Configuration

Inside:

```
src/api/
```

Example:

```ts
const BASE_URL = "http://localhost:8080";
```

---

# 🧪 Production Build

```bash
npm run build
```

---

# 💡 Design Goals

- Clean separation of responsibilities
- Scalable folder structure
- Type-safe development
- Professional organization
- Easy to extend

---

# 👨‍💻 Author

Martí  
Software Engineering Student
