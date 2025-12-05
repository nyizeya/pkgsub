CREATE TABLE app_users (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    encoded_password VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,

    role VARCHAR(50) NOT NULL,
    active TINYINT(1) NOT NULL,
    balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,

    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME NOT NULL,
    modified_at DATETIME,

    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;