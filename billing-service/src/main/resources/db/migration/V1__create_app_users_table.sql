CREATE TABLE app_users (
                           id VARCHAR(36) NOT NULL PRIMARY KEY,

                           username VARCHAR(30) NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           email VARCHAR(30) NOT NULL,

                           balance DECIMAL(19,2) NOT NULL DEFAULT 10000,

                           app_user_role VARCHAR(5) NOT NULL DEFAULT 'USER',

                           enabled VARCHAR(5) NOT NULL DEFAULT 1,
                           account_non_locked VARCHAR(5) NOT NULL DEFAULT 1,
                           account_non_expired VARCHAR(5) NOT NULL DEFAULT 1,
                           credentials_non_expired VARCHAR(5) NOT NULL DEFAULT 1,

                           credentials_expiry_date DATE NOT NULL,
                           account_expiry_date DATE NOT NULL,

                           created_by VARCHAR(30),
                           created_at DATETIME,
                           modified_by VARCHAR(30),
                           modified_at DATETIME
);

-- Unique constraints
ALTER TABLE app_users ADD CONSTRAINT uq_app_users_email UNIQUE (email);
ALTER TABLE app_users ADD CONSTRAINT uq_app_users_username UNIQUE (username);
