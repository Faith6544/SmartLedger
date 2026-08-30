-- SmartLedger Database Schema
-- Created: 2026-08-29
-- Updated: Added password column for user authentication

-- Drop existing database if needed (CAUTION: deletes all data!)
-- DROP DATABASE IF EXISTS smartledger;

-- Create database
CREATE DATABASE IF NOT EXISTS smartledger;
USE smartledger;

-- ============================================
-- USERS TABLE
-- Each business owner gets one row here
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    dashboard_token VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) DEFAULT 'password',
    business_name VARCHAR(100),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TRANSACTIONS TABLE
-- All sales, expenses, debts, supplies, payments go here
-- ============================================
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    type ENUM('SALE', 'EXPENSE', 'SUPPLY', 'DEBT', 'PAYMENT') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description TEXT,
    counterparty VARCHAR(100),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================
-- CHAT MESSAGES TABLE
-- Saves everything the user types, even if it's not a transaction
-- ============================================
CREATE TABLE IF NOT EXISTS chat_messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    is_transaction BOOLEAN DEFAULT FALSE,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================
-- INSERT SAMPLE DATA
-- ============================================

-- Sample user with password 'password'
INSERT INTO users (username, dashboard_token, password, business_name) 
VALUES ('testuser', 'test123-dashboard-token', 'password', 'Test Business');

-- Sample transactions
INSERT INTO transactions (user_id, type, amount, description, counterparty) VALUES
(1, 'SALE', 100000, 'Sold 5 bags of rice', 'Customer A'),
(1, 'EXPENSE', 5000, 'Paid for transport', 'Transport Company'),
(1, 'SUPPLY', 75000, 'Bought 10 crates of eggs', 'Supplier B'),
(1, 'DEBT', 20000, 'Customer owes for 2 bags of garri', 'Madam Bisi'),
(1, 'PAYMENT', 10000, 'Customer paid debt from last week', 'Mrs Adebayo');

-- Sample chat messages
INSERT INTO chat_messages (user_id, message, is_transaction) VALUES
(1, 'Sold 5 bags of rice for ₦100,000', TRUE),
(1, 'Paid ₦5,000 for transport', TRUE),
(1, 'hello, can you show my dashboard?', FALSE),
(1, 'Bought 10 crates of eggs for ₦75,000', TRUE),
(1, 'Madam Bisi owes ₦20,000 for 2 bags of garri', TRUE),
(1, 'Mrs Adebayo paid ₦10,000', TRUE);

-- ============================================
-- VERIFY DATA
-- ============================================
SELECT * FROM users;
SELECT * FROM transactions;
SELECT * FROM chat_messages;

-- ============================================
-- USEFUL QUERIES
-- ============================================

-- Get total sales
-- SELECT SUM(amount) as total_sales FROM transactions WHERE type = 'SALE';

-- Get total expenses
-- SELECT SUM(amount) as total_expenses FROM transactions WHERE type = 'EXPENSE';

-- Get total profit (sales - expenses - supplies)
-- SELECT 
--     SUM(CASE WHEN type = 'SALE' THEN amount ELSE 0 END) as sales,
--     SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) as expenses,
--     SUM(CASE WHEN type = 'SUPPLY' THEN amount ELSE 0 END) as supplies,
--     SUM(CASE WHEN type = 'SALE' THEN amount ELSE 0 END) - 
--     SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) - 
--     SUM(CASE WHEN type = 'SUPPLY' THEN amount ELSE 0 END) as profit
-- FROM transactions;

-- Get outstanding debts
-- SELECT SUM(amount) as total_debts FROM transactions WHERE type = 'DEBT';

-- All done!