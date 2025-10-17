SQL queries

CREATE DATABASE companydb;

USE companydb;

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    department VARCHAR(100)
);
