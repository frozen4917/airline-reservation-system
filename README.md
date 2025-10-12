# Airline Reservation System

A desktop-based airline reservation system built with Java Swing and MySQL. This user-centric application provides a centralized platform for passengers to register, search for flights across different airlines, book tickets, and manage their reservations in one place.

> [!NOTE]
> This project was developed as a part of Project-Based Learning for semester 3 for the subjects of Object Oriented Programming, Database Management Systems, Software Engineering, and Non-Linear Data Structures.

## Features

-   **User Authentication**: Secure user registration and login system.
-   **Flight Search**: Filter available flights by departure and arrival locations.
-   **Booking System**: Book tickets for multiple passengers in Economy or Business class with dynamic fare calculation.
-   **Reservation Management**: View a consolidated list of all personal bookings and cancel confirmed reservations.
-   **Password Hashing**: Demonstrates the FNV-1a hashing algorithm for educational purposes.

## Technologies Used

-   **Frontend**: Java Swing
-   **Backend**: Java
-   **Database**: MySQL
-   **Build Tool**: Apache Ant (via NetBeans)
-   **Dependencies**:
    -   JCalendar 1.4
    -   MySQL Connector/J

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need the following software installed on your computer:

-   Java Development Kit (JDK) 8 or higher
-   MySQL Server
-   Apache NetBeans IDE

### Installation and Setup

#### **1. Clone the repository**

```sh
git clone https://github.com/frozen4917/airline-reservation-system.git
```

#### **2. Database Setup**

-   Start your local MySQL server.
-   Import the database schema by executing the `DATABASE_CREATION.sql` file provided in the repository. This creates a database called `airline_reservations`
-   Populate the database with sample flight data by executing the `FLIGHTS.sql` file.

#### **3. Configure the Application**

> [!IMPORTANT]
> You must create a configuration file with your database password.
-   Navigate to the `src` folder inside the project directory.
-   Create a copy of the `config.properties.template` file.
-   Rename the copied file to `config.properties`.
-   Open `config.properties` and update the `DB_PASS` field with your MySQL password.

#### **4. Build the Project in NetBeans**
-   Open the project folder in Apache NetBeans.
-   The IDE should automatically recognize the project and its dependencies from the `lib` folder.
-   Right-click the project in the "Projects" pane and select "Clean and Build".

### Running the Application

After a successful build, you can run the application in two ways:

1.  **From NetBeans**: Right-click the project and select "Run".
2.  **From the command line**: Navigate to the `dist` folder that was created by the build process and execute the runnable JAR file:
    ```sh
    java -jar AIRLINE.jar
    ```

## ⚠️ Security Warning

> [!WARNING]
> **FNV-1a Hashing Algorithm**
>
> The FNV-1a hashing algorithm implemented in this project is for **academic and demonstrative purposes only** as a part of **hashing** in the curriculum of Non-Linear Data Structures.
> 
> It is a non-cryptographic hash function and is **NOT SECURE** for storing passwords in a real-world, production environment.
> For production systems, always use a modern, salted, and cryptographically secure hashing algorithm like Argon2, scrypt, or bcrypt.

## What I Learned

- **GUI development**: Building user-friendly interfaces with Java Swing components (JFrame, JPanel, JTable)
- **Database integration**: Connecting Java applications to MySQL using JDBC
- **SQL queries**: Writing complex queries for user login/registration, searching flights, and booking
- **Event-driven programming**: Handling button clicks, form submissions, and user interactions
- **Hashing Algorithms**: Implementing a basic hashing algorithm(in this case, FNV-1a) from scratch to understand hashing fundamentals
- **SQL injection prevention**: Using PreparedStatement to parameterize queries
- **Data validation**: Client-side and server-side validation for user inputs
- **Database design**: Creating normalized schemas with primary keys, foreign keys, and relationships
- **Project planning**: Breaking down a large project into manageable modules
- **Testing**: Manually testing various user flows and edge cases