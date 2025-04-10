-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS gestaoramais CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Selecionar o banco
USE gestaoramais;

-- Tabela USUARIO
CREATE TABLE IF NOT EXISTS usuario (
    id BINARY(16) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela RAMAL
CREATE TABLE IF NOT EXISTS ramal (
    id BINARY(16) NOT NULL PRIMARY KEY,
    numero VARCHAR(10) NOT NULL UNIQUE,
    usuario_logado_id BINARY(16),
    CONSTRAINT fk_usuario_logado
        FOREIGN KEY (usuario_logado_id)
        REFERENCES usuario(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
