-- SmartLedger Database Schema
-- Created: 2026-08-29

CREATE DATABASE IF NOT EXISTS smartledger;
USE smartledger;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    dashboard_token VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Transactions table
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type ENUM('SALE', 'EXPENSE', 'SUPPLY', 'DEBT', 'PAYMENT') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description TEXT,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Chat messages table
CREATE TABLE IF NOT EXISTS chat_messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Sample user
INSERT INTO users (username, dashboard_token) 
VALUES ('testuser', 'test123-dashboard-token');

-- Sample transactions
INSERT INTO transactions (user_id, type, amount, description) VALUES
(1, 'SALE', 100000, 'Sold 5 bags of rice'),
(1, 'EXPENSE', 5000, 'Paid for transport'),
(1, 'SUPPLY', 75000, 'Bought 10 crates of eggs'),
(1, 'DEBT', 20000, 'Customer owes for 2 bags of garri'),
(1, 'PAYMENT', 10000, 'Customer paid debt from last week');

-- Sample chat messages
INSERT INTO chat_messages (user_id, message) VALUES
(1, 'Sold 5 bags of rice for ₦100,000'),
(1, 'Paid ₦5,000 for transport'),
(1, 'hello, can you show my dashboard?'),
(1, 'Bought 10 crates of eggs for ₦75,000'),
(1, 'Madam Bisi owes ₦20,000 for 2 bags of garri'),
(1, 'Mrs Adebayo paid ₦10,000');