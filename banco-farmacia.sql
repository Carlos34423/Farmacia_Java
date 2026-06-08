farmaciafarmaciaCREATE DATABASE IF NOT EXISTS farmacia
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE farmacia;

CREATE TABLE IF NOT EXISTS cidade (
    codigoCidade INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    uf CHAR(2) NOT NULL,
    PRIMARY KEY (codigoCidade)
);

CREATE TABLE IF NOT EXISTS cargo (
    codigoCargo INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    salarioBase DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (codigoCargo)
);

CREATE TABLE IF NOT EXISTS categoriaMedicamento (
    codigoCategoriaMedicamento INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    descricao VARCHAR(255),
    PRIMARY KEY (codigoCategoriaMedicamento)
);

CREATE TABLE IF NOT EXISTS convenio (
    codigoConvenio INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    percentualDesconto DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (codigoConvenio)
);

CREATE TABLE IF NOT EXISTS fabricante (
    cnpj VARCHAR(20) NOT NULL,
    nome VARCHAR(120) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(120),
    PRIMARY KEY (cnpj)
);

CREATE TABLE IF NOT EXISTS formaPagamento (
    codigoFormaPagamento INT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(80) NOT NULL,
    PRIMARY KEY (codigoFormaPagamento)
);

CREATE TABLE IF NOT EXISTS fornecedor (
    codigoFornecedor INT NOT NULL AUTO_INCREMENT,
    razaoSocial VARCHAR(120) NOT NULL,
    nomeFantasia VARCHAR(120),
    cnpj VARCHAR(20) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(120),
    cidade VARCHAR(80),
    PRIMARY KEY (codigoFornecedor),
    UNIQUE KEY uk_fornecedor_cnpj (cnpj)
);
