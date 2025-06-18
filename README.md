# 🎓 Student Management System - JavaFX + MySQL 🚀

![JavaFX](https://img.shields.io/badge/JavaFX-GUI-blue.svg)
![MySQL](https://img.shields.io/badge/Database-MySQL-orange.svg)
![JDBC](https://img.shields.io/badge/JDBC-Connector-green.svg)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen.svg)

> 📘 **A complete desktop-based Student Management System built with JavaFX and MySQL.**  
> 🔄 Handles real-time insertions and displays for students, courses, and enrollments.  
> 🎯 Designed for simplicity, performance, and academic use-cases.

---

## ✨ Features That Shine

🌟 **Insert Data** – Add students and courses with a single click  
📚 **Many-to-Many Mapping** – Automatically enroll students into selected courses  
🔍 **Real-time Data Fetch** – Instantly display all stored records  
🛡 **Validation & Error Handling** – Catch duplicate IDs, empty fields, and invalid input gracefully  
🖼 **Simple UI** – Clean and intuitive JavaFX interface  
⚡ **Fully Functional** – Backed by JDBC and MySQL relational integrity

---

## 📸 Application UI Preview

Here’s a live screenshot of the system in action:

![Student Management System Screenshot](./Screenshot.png)

> 📌 The app displays data fetched from the MySQL database after inserting records from the JavaFX interface.

---

## 🧱 Database Schema Setup

Before running the app, create the required database schema:

```sql
CREATE DATABASE stdinfo;
USE stdinfo;

CREATE TABLE students (
  student_id INT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100)
);

CREATE TABLE courses (
  course_id INT PRIMARY KEY,
  name VARCHAR(100)
);

CREATE TABLE enrollments (
  student_id INT,
  course_id INT,
  FOREIGN KEY (student_id) REFERENCES students(student_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id)
);
