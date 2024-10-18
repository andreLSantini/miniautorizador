CREATE TABLE cartao (
    id_cartao CHAR(36) NOT NULL,
    numero_cartao VARCHAR(16) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    saldo DECIMAL(19,2) NOT NULL,
    PRIMARY KEY (id_cartao)
);
