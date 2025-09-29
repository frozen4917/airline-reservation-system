-- Create Database
CREATE DATABASE IF NOT EXISTS airline_reservations;
USE airline_reservations;

-- Table: AIRPORT
CREATE TABLE AIRPORT (
    AirportCode VARCHAR(3) PRIMARY KEY,
    AirportName VARCHAR(100),
    City VARCHAR(30),
    Country VARCHAR(30)
);

-- Table: AIRLINES
CREATE TABLE AIRLINES (
    AirlineID INT PRIMARY KEY,
    AirlineName VARCHAR(30)
);

-- Table: FLIGHTS
CREATE TABLE FLIGHTS (
    FlightNumber VARCHAR(6) PRIMARY KEY,
    DepartureAirport VARCHAR(3),
    ArrivalAirport VARCHAR(3),
    DepTime DATETIME,
    ArrTime DATETIME,
    AirlineID INT,
    Fare INT,
    CONSTRAINT fk_departure_airport FOREIGN KEY (DepartureAirport) REFERENCES AIRPORT(AirportCode),
    CONSTRAINT fk_arrival_airport FOREIGN KEY (ArrivalAirport) REFERENCES AIRPORT(AirportCode),
    CONSTRAINT fk_airline FOREIGN KEY (AirlineID) REFERENCES AIRLINES(AirlineID)
);

-- Table: PASSENGER
CREATE TABLE PASSENGER (
    PaxID VARCHAR(8) PRIMARY KEY,
    Name VARCHAR(50),
    Gender VARCHAR(1),
    DoB DATE,
    PassportNo VARCHAR(8)
);

-- Table: RESERVATIONS
CREATE TABLE RESERVATIONS (
    PNR VARCHAR(7) PRIMARY KEY,
    PaxID VARCHAR(8),
    FlightNumber VARCHAR(6),
    EconomySeats INT DEFAULT 0,
    BusinessSeats INT DEFAULT 0,
    TotalFare INT,
    Status VARCHAR(20),
    CONSTRAINT fk_res_pax FOREIGN KEY (PaxID) REFERENCES PASSENGER(PaxID),
    CONSTRAINT fk_res_flight FOREIGN KEY (FlightNumber) REFERENCES FLIGHTS(FlightNumber)
);

-- Table: USERS
CREATE TABLE USERS (
    Username VARCHAR(20) COLLATE utf8mb4_bin PRIMARY KEY, -- case-sensitive
    Password VARCHAR(128) COLLATE utf8mb4_bin,
    PaxID VARCHAR(8),
    PasswordHash CHAR(16) COLLATE utf8mb4_bin,
    CONSTRAINT fk_user_pax FOREIGN KEY (PaxID) REFERENCES PASSENGER(PaxID)
);