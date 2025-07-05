CREATE DATABASE IF NOT EXISTS helpdesk CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE helpdesk;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('user', 'support', 'admin') NOT NULL
);


CREATE TABLE IF NOT EXISTS tickets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('New', 'In Progress', 'Resolved') NOT NULL DEFAULT 'New',
    priority ENUM('Low', 'Medium', 'High') NOT NULL DEFAULT 'Medium',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (username, password, role) VALUES 
('sudenur_admin', 'admin123', 'admin'),
('sudenur_support', 'support123', 'support'),
('sudenur_user1', 'user123', 'user'),
('sudenur_user2', 'user456', 'user');

INSERT INTO tickets (user_id, title, description, status, priority)
VALUES
(3, 'Password Reset', 'I cannot access my account', 'New', 'High');
