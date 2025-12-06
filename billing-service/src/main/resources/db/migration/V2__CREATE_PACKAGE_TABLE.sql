CREATE TABLE app_packages (
                              id VARCHAR(36) NOT NULL PRIMARY KEY,

                              name VARCHAR(255) NOT NULL,
                              opened_date DATE NOT NULL,
                              closed_date DATE NOT NULL,

                              permitted_count INT NOT NULL,
                              available_count INT NOT NULL,

                              price DECIMAL(19,2) NOT NULL,

                              created_by VARCHAR(255),
                              modified_by VARCHAR(255),

                              created_at DATETIME(6),
                              modified_at DATETIME(6)
);

ALTER TABLE app_packages ADD CONSTRAINT uq_app_packages_name UNIQUE (name);