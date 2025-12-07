CREATE TABLE app_subscriptions (
                                   id VARCHAR(36) NOT NULL,

                                   user_id VARCHAR(255) NOT NULL,
                                   package_id VARCHAR(255) NOT NULL,
                                   amount DECIMAL(10, 2) NOT NULL,
                                   status VARCHAR(50) NOT NULL,
                                   refunded_at DATE,

                                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   created_by VARCHAR(255),
                                   modified_by VARCHAR(255),

                                   PRIMARY KEY (id),

                                   CONSTRAINT uc_user_package UNIQUE (user_id, package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
