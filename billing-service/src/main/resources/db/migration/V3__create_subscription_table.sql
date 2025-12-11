CREATE TABLE app_subscriptions (
                                   id VARCHAR(36) NOT NULL,

                                   user_id VARCHAR(36) NOT NULL,
                                   package_id VARCHAR(36) NOT NULL,
                                   package_name VARCHAR(30) NOT NULL,
                                   amount DECIMAL(10, 2) NOT NULL,
                                   status VARCHAR(8) NOT NULL,
                                   refunded_at DATE,

                                   created_at DATETIME,
                                   modified_at DATETIME,
                                   created_by VARCHAR(30),
                                   modified_by VARCHAR(30),

                                   PRIMARY KEY (id),

                                   CONSTRAINT uc_user_package UNIQUE (user_id, package_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
