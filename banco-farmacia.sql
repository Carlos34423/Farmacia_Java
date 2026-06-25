CREATE DATABASE IF NOT EXISTS farmacia
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
    codigoCidade INT NOT NULL,
    PRIMARY KEY (codigoFornecedor),
    UNIQUE KEY uk_fornecedor_cnpj (cnpj),
    CONSTRAINT fk_fornecedor_cidade FOREIGN KEY (codigoCidade) REFERENCES cidade(codigoCidade)
);

CREATE TABLE IF NOT EXISTS cliente (
    codigoCliente INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(120),
    codigoCidade INT NOT NULL,
    codigoConvenio INT NOT NULL,
    PRIMARY KEY (codigoCliente),
    UNIQUE KEY uk_cliente_cpf (cpf),
    CONSTRAINT fk_cliente_cidade FOREIGN KEY (codigoCidade) REFERENCES cidade(codigoCidade),
    CONSTRAINT fk_cliente_convenio FOREIGN KEY (codigoConvenio) REFERENCES convenio(codigoConvenio)
);

CREATE TABLE IF NOT EXISTS funcionario (
    codigoFuncionario INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(120),
    codigoCargo INT NOT NULL,
    codigoCidade INT NOT NULL,
    PRIMARY KEY (codigoFuncionario),
    UNIQUE KEY uk_funcionario_cpf (cpf),
    CONSTRAINT fk_funcionario_cargo FOREIGN KEY (codigoCargo) REFERENCES cargo(codigoCargo),
    CONSTRAINT fk_funcionario_cidade FOREIGN KEY (codigoCidade) REFERENCES cidade(codigoCidade)
);

CREATE TABLE IF NOT EXISTS medicamento (
    codigoMedicamento INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    descricao VARCHAR(255),
    precoVenda DECIMAL(10,2) NOT NULL,
    quantidadeEstoque INT NOT NULL,
    codigoCategoriaMedicamento INT NOT NULL,
    cnpjFabricante VARCHAR(20) NOT NULL,
    codigoFornecedor INT NOT NULL,
    PRIMARY KEY (codigoMedicamento),
    CONSTRAINT fk_medicamento_categoria FOREIGN KEY (codigoCategoriaMedicamento) REFERENCES categoriaMedicamento(codigoCategoriaMedicamento),
    CONSTRAINT fk_medicamento_fabricante FOREIGN KEY (cnpjFabricante) REFERENCES fabricante(cnpj),
    CONSTRAINT fk_medicamento_fornecedor FOREIGN KEY (codigoFornecedor) REFERENCES fornecedor(codigoFornecedor)
);
