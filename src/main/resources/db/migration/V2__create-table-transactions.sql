CREATE TABLE transactions(
    id_transaction UUID NOT NULL,
    description VARCHAR(50) NOT NULL,
    value_transaction FLOAT NOT NULL,
    category VARCHAR(15) NOT NULL,
    date_transaction DATE NOT NULL,
    type_transaction VARCHAR(10) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    id_user UUID NOT NULL,
    CONSTRAINT PK_ID_TRANSACTION PRIMARY KEY (id_transaction),
    CONSTRAINT FK_ID_USER FOREIGN KEY (id_user) REFERENCES users (id_user)
);