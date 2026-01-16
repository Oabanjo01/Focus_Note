# Focus Notes

## Technical Architecture & Workflow Documentation

### Document Purpose

This document serves as a comprehensive technical specification and learning artifact for the **Focus Notes** application. It is intentionally structured to reflect production-grade engineering standards, suitable for enterprise review, academic evaluation, and long-term scalability planning.

The system is designed using modern, industry-aligned technologies:

* **Backend:** .NET 10 Minimal APIs
* **Frontend:** Kotlin with Jetpack Compose (Android)
* **Architecture Style:** Client–Server, Clean Architecture (Lite), MVVM

---

## 1. High-Level System Architecture

### 1.1 Architectural Overview

The application adopts a **Client–Server Architecture**, ensuring clear separation of concerns, independent scalability, and maintainability.

**System Components:**

* **Mobile Client (Android):**

  * Responsible for user interaction, UI rendering, and local state handling.
  * Communicates with the backend via RESTful HTTP APIs.

* **Backend API (.NET 8):**

  * Encapsulates business logic, validation rules, and data persistence.
  * Exposes a stable contract via DTOs.

* **Database Layer:**

  * **PostgreSQL** for production deployments.
  * **SQLite** for local development and testing.

### 1.2 Logical Data Flow

```
User Action
   ↓
Jetpack Compose UI
   ↓
ViewModel (StateFlow)
   ↓
Repository
   ↓
Retrofit (HTTP)
   ↓
.NET Minimal API Endpoint
   ↓
Entity Framework Core
   ↓
Database
```

---

## 2. Backend Architecture (.NET 8 Minimal API)

### 2.1 Design Rationale

The backend intentionally uses **Minimal APIs** to:

* Reduce boilerplate and cognitive overhead
* Encourage functional, readable request pipelines
* Accelerate learning while preserving architectural rigor

This approach balances procedural clarity with object-oriented structure.

---

### 2.2 Folder Structure (Clean Architecture Lite)

```
📂 FocusNotes.API
├── 📂 Data
│   ├── AppDbContext.cs
│   └── Migrations/
├── 📂 Entities
│   └── Note.cs
├── 📂 Dtos
│   ├── CreateNoteDto.cs
│   ├── UpdateNoteDto.cs
│   └── NoteResponseDto.cs
├── 📂 Endpoints
│   └── NoteEndpoints.cs
├── 📂 Extensions
│   └── DataExtensions.cs
├── appsettings.json
└── Program.cs
```

**Responsibility Allocation:**

* `Entities` define the domain truth.
* `Dtos` define the public API contract.
* `Endpoints` orchestrate request handling.
* `Data` manages persistence and migrations.

---

### 2.3 Domain Entity Model

```csharp
public class Note
{
    public int Id { get; set; }
    public required string Title { get; set; }
    public string? Content { get; set; }
    public bool IsCompleted { get; set; }
    public DateTime CreatedAt { get; set; }
}
```

**Design Notes:**

* The entity is persistence-focused.
* It is never returned directly to the client.

---

### 2.4 Data Transfer Objects (DTOs)

```csharp
public record CreateNoteDto(string Title, string? Content);
public record UpdateNoteDto(string Title, string? Content, bool IsCompleted);
```

**Strategic Benefits:**

* Decouples API consumers from schema evolution
* Enables validation and versioning
* Encourages immutability

---

### 2.5 REST API Contract

| Method | Endpoint    | Responsibility                      |
| ------ | ----------- | ----------------------------------- |
| GET    | /notes      | Retrieve all notes (sorted by date) |
| GET    | /notes/{id} | Retrieve a single note              |
| POST   | /notes      | Create a new note                   |
| PUT    | /notes/{id} | Update an existing note             |
| DELETE | /notes/{id} | Remove a note                       |

---

## 3. Frontend Architecture (Android – Kotlin)

### 3.1 UI Architecture Pattern

The Android client follows the **Model–View–ViewModel (MVVM)** pattern.

**Separation of Responsibilities:**

* **View (Compose UI):** Rendering only
* **ViewModel:** State management and orchestration
* **Repository:** Data source abstraction

---

### 3.2 Project Structure

```
📂 com.example.focusnotes
├── 📂 data
│   ├── 📂 model
│   ├── 📂 remote
│   └── NotesRepository.kt
├── 📂 ui
│   ├── 📂 theme
│   ├── 📂 noteslist
│   │   ├── NotesListScreen.kt
│   │   └── NotesListViewModel.kt
│   └── 📂 notedetail
│       ├── NoteDetailScreen.kt
│       └── NoteDetailViewModel.kt
└── MainActivity.kt
```

---

### 3.3 Core Client Technologies

| Technology      | Purpose                   |
| --------------- | ------------------------- |
| Jetpack Compose | Declarative UI framework  |
| Retrofit        | HTTP networking           |
| StateFlow       | Reactive state management |
| Coroutines      | Asynchronous execution    |
| Hilt (Optional) | Dependency Injection      |

---

## 4. End-to-End Workflow

### 4.1 Note Retrieval Workflow

1. Application launches
2. ViewModel triggers data fetch
3. Repository calls Retrofit service
4. Backend API retrieves data via EF Core
5. DTOs returned as JSON
6. StateFlow emits new state
7. UI recomposes automatically

---

## 5. Incremental Learning Roadmap

### Phase 1: API Foundation

**Objective:** Validate backend setup

* Minimal API returning hardcoded notes
* Swagger-based verification

### Phase 2: Client Integration

**Objective:** Establish network communication

* Android Compose UI
* Retrofit integration
* LazyColumn rendering

### Phase 3: Persistence Layer

**Objective:** Introduce real storage

* EF Core with SQLite
* Migrations and async queries

### Phase 4: Full CRUD Capability

**Objective:** Enable user interaction

* Create and delete notes
* Form handling and validation

---

## 6. Strategic Alignment

### 6.1 Engineering Fit

* Encourages architectural thinking over ad-hoc coding
* Mirrors enterprise-grade mobile-backend systems
* Reinforces scalable design principles

### 6.2 Academic and Industry Relevance

* Demonstrates applied software architecture knowledge
* Aligns with production patterns used in fintech, mobility, and research-driven teams

---

## 7. Next Implementation Options

* Backend bootstrap (`Program.cs` – Phase 1)
* Android Retrofit and networking layer

Both paths are independent and can be developed in parallel, reinforcing modular thinking.
