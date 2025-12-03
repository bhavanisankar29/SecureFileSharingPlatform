# 🚀 Secure File Storage Platform

A full-stack web application in which users can create an account to securely upload, view, download, and delete their files.

The project is built using a **Spring Boot** backend for secure API handling and file management, paired with a modern **React.js** frontend for a responsive user experience.

---

## 📋 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
  - [1. Database Setup](#1-database-setup)
  - [2. Backend Setup](#2-backend-setup)
  - [3. Frontend Setup](#3-frontend-setup)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)

---

## ✨ Features
*   **User Authentication**: Secure Login and Registration system using Spring Security.
*   **File Upload**: Upload files of any type (images, docs, pdfs, etc.).
*   **File Dashboard**: View a list of uploaded files with metadata (File Name, Size, Upload Date).
*   **Secure Storage**: Files are stored in the local server filesystem, segregated by User ID.
*   **Download & Delete**: authorized users can download or remove their files.
*   **Session Management**: Uses HttpOnly cookies and JSESSIONID for security.

---

## 🛠 Tech Stack

### Backend
*   **Language**: Java (JDK 17 or 21)
*   **Framework**: Spring Boot 3+
*   **Security**: Spring Security (Session-based Auth, BCrypt)
*   **Database**: MySQL
*   **ORM**: Spring Data JPA / Hibernate
*   **Storage**: Local File System

### Frontend
*   **Library**: React.js (v18)
*   **Build Tool**: Vite
*   **HTTP Client**: Axios
*   **Routing**: React Router DOM
*   **Styling**: CSS3

---

## 📂 Project Structure

```text
file-storage-platform/
│
├── backend/                 # Spring Boot Application
│   ├── src/main/java...     # Controllers, Services, Repositories
│   ├── uploads/             # (Auto-generated) Stores user files
│   └── pom.xml              # Maven dependencies
│
├── filestorage/                # React Application
│   ├── src/                 # React Components (Login, Dashboard)
│   ├── vite.config.js       # Vite Configuration
│   └── package.json         # Node dependencies
│
└── README.md
```
## 🔌 Prerequisites
Before running the project, ensure you have the following installed:
1.  **Java Development Kit (JDK)** 17 or higher.
2.  **Maven** (or use the `mvnw` wrapper included in Spring Boot).
3.  **Node.js** & **npm** (for React).
4.  **MySQL Server**.

---

## 🚀 Installation & Setup

### 1. Database Setup
Open your MySQL Workbench or terminal and create the database:
```sql
CREATE DATABASE filestorage_db;
```
### 2. Backend Setup
Navigate to the backend folder:
```bash
cd backend
```
Configure your database credentials. Open src/main/resources/application.properties and edit:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/filestorage_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```
Run the application:
```bash
mvn spring-boot:run
```
### 3. Frontend Setup
Open a new terminal and navigate to the frontend folder:
```bash
cd filestorage
```
Install dependencies:
```bash
npm install
```
Start the development server:
```bash
npm run dev
```
The frontend will start on http://localhost:5173

## Usage
*   **Open your browser and go to http://localhost:5173.**
*   **Register: Click "Register" to create a new account.**
*   **Login: Use your new credentials to log in.**
*   **Upload: Click "Choose File" and then "Upload" to save a file.**
*   **Manage: Use the "Download" or "Delete" buttons in the file table.**

## 📡 API Endpoints

| Method | Endpoint | Description | Request Body / Params |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | Register a new user | `username`, `password` (x-www-form-urlencoded) |
| **POST** | `/login` | Authenticate user (Spring Security) | `username`, `password` (x-www-form-urlencoded) |
| **POST** | `/logout` | Logout the current user | N/A |
| **POST** | `/files/upload` | Upload a new file | `file` (multipart/form-data) |
| **GET** | `/files/list` | Retrieve list of uploaded files | N/A |
| **GET** | `/files/download/{id}` | Download a specific file | Path Variable: `id` |
| **DELETE** | `/files/delete/{id}` | Delete a specific file | Path Variable: `id` |
