CREATE TABLE users(
    id_user UUID,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(30) NOT NULL UNIQUE,
    age INTEGER,
    password VARCHAR(255) NOT NULL, CHECK (LENGTH(password) >= 6),
    reset_password_token VARCHAR(255),
    gender VARCHAR(15),
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT PK_ID_USER PRIMARY KEY (id_user)
);