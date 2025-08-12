# University Management System 🎓

![Java](https://img.shields.io/badge/Java-17-orange) ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue) ![UI](https://img.shields.io/badge/UI-Java%20Swing-red)

A comprehensive, desktop-based University Management System built with **Java Swing** and **MySQL**. This application provides a modern, secure, and user-friendly interface for managing student and faculty data, academic records, and administrative tasks.

This project was initially inspired by the "TechCoder A.V." YouTube playlist and was then completely refactored and enhanced with modern UI/UX, robust security features, and improved code architecture.

---

## 🚀 Live Demo / Showcase

<iframe width="560" height="315" src="https://www.youtube.com/embed/LlfmZvvcjS8?si=vPYCbqkRGGAvsOTO" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

---

## ✨ Key Features

* **Secure User Login:** A full-screen, modern login window with show/hide password functionality.
* **Desktop-Style Interface (MDI):** A professional main window where all forms open as movable, resizable, and maximizable "internal" windows within a central workspace.
* **Student & Faculty Management:** Full CRUD (Create, Read, Update) capabilities for detailed student and faculty records.
* **Academic Records:** Modules for entering student subjects and marks, and for viewing a formatted results sheet.
* **Leave Management:** A system for both students and faculty to apply for leave.
* **Fee Processing:** Functionality to view the university's fee structure and process student fee payments.
* **Modern Look & Feel:** A consistent, modern dark theme powered by the FlatLaf library.

---

## 📸 Screenshots

| Login Screen | Main Desktop | Add Student Form |
| :---: | :---: | :---: |
| [<img width="1244" height="750" alt="image" src="https://github.com/user-attachments/assets/ea001f88-d342-4a51-8d7b-f30b80dc6028" />
] | [<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/d32933aa-afde-4916-9e0b-bde68a5aa82e" />
] | [<img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/0590b113-9d01-4234-8651-2bba5e6406f1" />
] |


---

## 🛠️ Tech Stack

* **Language:** **Java** (JDK 17)
* **UI Framework:** **Java Swing**
* **Database:** **MySQL 8.0** with **JDBC**
* **UI Theming:** **FlatLaf** Look and Feel
* **Libraries:**
    * `mysql-connector-j` for database connectivity.
    * `JCalendar` for date selection components.
    * `rs2xml` for easy `ResultSet` to `JTable` conversion.
    * `flatlaf-extras` for SVG icon support.

---

## ⚙️ Setup and Installation

To get this project running locally, follow these steps:

### 1. Prerequisites
* **Java Development Kit (JDK) 17** or later.
* **MySQL Server 8.0** or later.
* An IDE like **IntelliJ IDEA** or Eclipse.

### 2. Database Setup
The complete SQL script to set up your database is included in this repository.
1.  Open your MySQL client (like MySQL Workbench).
2.  Run the entire script provided in the **`Query.sql`** file. This will:
    * Create the `university_management_DB` database.
    * Create all the required tables (`student`, `teacher`, `login`, `fee`, etc.).
    * Insert initial sample data to get you started.

### 3. Project Configuration
1.  **Clone the repository:**
    ```bash
    git clone [YOUR-REPOSITORY-LINK]
    ```
2.  **Open the project** in your IDE.
3.  **Configure Libraries:** Make sure all the necessary `.jar` files are included in your project's classpath. In IntelliJ, you can do this by right-clicking the `jar` files in the `jar_con` folder and selecting "Add as Library...". The required libraries are:
    * `flatlaf-3.6.jar` (or newer)
    * `flatlaf-extras-....jar`
    * `jcalendar-tz-....jar`
    * `mysql-connector-java-....jar`
    * `ResultSet2xml.jar`
4.  **Database Connection:** Open the `ConnectionForSystem.java` file and update the database credentials if they are different from the default:
    ```java
    connection = DriverManager.getConnection("jdbc:mysql:///university_management_DB", "YOUR-USERNAME", "YOUR-PASSWORD");
    ```

### 4. Running the Application
The main entry point for the application is the **`Splash.java`** file. Run this file to start the application.

---

## 🙏 Acknowledgments
* This project was inspired by the foundational concepts taught in the **TechCoder A.V.** YouTube series.
