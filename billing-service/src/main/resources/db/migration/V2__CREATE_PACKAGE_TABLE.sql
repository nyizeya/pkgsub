CREATE TABLE app_packages (
                              id VARCHAR(36) NOT NULL PRIMARY KEY,

                              name VARCHAR(30) NOT NULL,
                              opened_date DATE NOT NULL,
                              closed_date DATE NOT NULL,

                              permitted_count INT NOT NULL,
                              available_count INT NOT NULL,

                              price DECIMAL(19,2) NOT NULL,

                              created_by VARCHAR(30),
                              modified_by VARCHAR(30),

                              created_at DATETIME,
                              modified_at DATETIME
);

ALTER TABLE app_packages ADD CONSTRAINT uq_app_packages_name UNIQUE (name);