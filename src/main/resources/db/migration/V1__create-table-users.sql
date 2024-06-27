CREATE TABLE users (
    id CHAR(26) PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
)
