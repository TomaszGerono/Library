# 📚 Library Manager

A desktop application for managing a personal book library, built with **JavaFX**, **Spring Boot**, and **JPA (Hibernate)**.  
The project was developed as an **academic assignment**, with a strong focus on clean architecture, GUI–backend integration, and relational data modeling.

---

## ✨ Features

### 📖 Book Management
- add, edit, and delete books
- support for multiple authors (many-to-many relationship)
- book details: genre, publication year, ISBN, number of pages
- reading progress tracking: `unread / reading / completed`
- personal notes for each book

### 🔍 Search & Filtering
- filtering by:
    - title
    - author (full name)
    - genre
    - publication year
- table sorting
- live results counter

### 🗂️ Topics (Custom Shelves)
- create and delete topics
- assign books to topics
- remove books from topics
- view all books assigned to a selected topic

### 🧩 Categorization
- group books by:
    - genre
    - author
    - series
- dynamic group lists
- preview books within the selected category

### 📊 Overview (Dashboard)
- library summary:
    - total number of books
    - number of topics
    - unread / reading / completed breakdown
- book recommendations list
- visual KPI cards and quote section

### 📥 Data Import
- import books from **JSON files**
- import from Google Books API (prepared structure)
- automatic handling of authors, genres, and topics during import

---

## 🏗️ Architecture

- **JavaFX** – user interface
- **Spring Boot** – backend and dependency injection
- **Spring Data JPA (Hibernate)** – persistence layer
- **SQLite** – embedded database
- **MVVM** – separation of view logic from controllers
- **FXML** – UI layout definitions
- **CSS** – consistent, modern UI styling
- **TestFX** – UI testing

---

## 🧠 Key Technical Concepts

- `@ManyToMany` relationships (Books ↔ Authors, Books ↔ Topics)
- global `SelectionBus` for cross-view communication
- asynchronous database operations (`Task`, single-thread queue)
- safe UI refresh without infinite query loops
- JSON import mapped to JPA entities
- dynamic filtering with `FilteredList` + `SortedList`
- scoped CSS styling without affecting global JavaFX controls

---

## 🗄️ Database

- **SQLite**
- automatic schema creation (`ddl-auto=create` in dev profile) for presentation purposes
- sample data initialization (Polish literature & IT books)
- sequences with custom initial values

---

## ▶️ Running the Application

### Requirements
- Java **21+**
- Gradle
- IntelliJ IDEA (recommended)

### Run
```bash
./gradlew run

