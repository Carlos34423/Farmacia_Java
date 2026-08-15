-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           8.0.46 - MySQL Community Server - GPL
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.17.0.7270
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para farmacia
DROP DATABASE IF EXISTS `farmacia`;
CREATE DATABASE IF NOT EXISTS `farmacia` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `farmacia`;

-- Copiando estrutura para tabela farmacia.cargo
DROP TABLE IF EXISTS `cargo`;
CREATE TABLE IF NOT EXISTS `cargo` (
  `codigoCargo` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `salarioBase` decimal(10,2) NOT NULL,
  PRIMARY KEY (`codigoCargo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.categoriamedicamento
DROP TABLE IF EXISTS `categoriamedicamento`;
CREATE TABLE IF NOT EXISTS `categoriamedicamento` (
  `codigoCategoriaMedicamento` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `descricao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`codigoCategoriaMedicamento`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.cidade
DROP TABLE IF EXISTS `cidade`;
CREATE TABLE IF NOT EXISTS `cidade` (
  `codigoCidade` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `uf` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`codigoCidade`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.cliente
DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `codigoCliente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigoCidade` int NOT NULL,
  `codigoConvenio` int NOT NULL,
  `carteira` float DEFAULT NULL,
  PRIMARY KEY (`codigoCliente`),
  UNIQUE KEY `uk_cliente_cpf` (`cpf`),
  KEY `fk_cliente_cidade` (`codigoCidade`),
  KEY `fk_cliente_convenio` (`codigoConvenio`),
  CONSTRAINT `fk_cliente_cidade` FOREIGN KEY (`codigoCidade`) REFERENCES `cidade` (`codigoCidade`),
  CONSTRAINT `fk_cliente_convenio` FOREIGN KEY (`codigoConvenio`) REFERENCES `convenio` (`codigoConvenio`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.convenio
DROP TABLE IF EXISTS `convenio`;
CREATE TABLE IF NOT EXISTS `convenio` (
  `codigoConvenio` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `percentualDesconto` decimal(5,2) NOT NULL,
  PRIMARY KEY (`codigoConvenio`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.fabricante
DROP TABLE IF EXISTS `fabricante`;
CREATE TABLE IF NOT EXISTS `fabricante` (
  `cnpj` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nome` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.formapagamento
DROP TABLE IF EXISTS `formapagamento`;
CREATE TABLE IF NOT EXISTS `formapagamento` (
  `codigoFormaPagamento` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`codigoFormaPagamento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.fornecedor
DROP TABLE IF EXISTS `fornecedor`;
CREATE TABLE IF NOT EXISTS `fornecedor` (
  `codigoFornecedor` int NOT NULL AUTO_INCREMENT,
  `razaoSocial` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nomeFantasia` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cnpj` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigoCidade` int NOT NULL,
  PRIMARY KEY (`codigoFornecedor`),
  UNIQUE KEY `uk_fornecedor_cnpj` (`cnpj`),
  KEY `fk_fornecedor_cidade` (`codigoCidade`),
  CONSTRAINT `fk_fornecedor_cidade` FOREIGN KEY (`codigoCidade`) REFERENCES `cidade` (`codigoCidade`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.funcionario
DROP TABLE IF EXISTS `funcionario`;
CREATE TABLE IF NOT EXISTS `funcionario` (
  `codigoFuncionario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigoCargo` int NOT NULL,
  `codigoCidade` int NOT NULL,
  PRIMARY KEY (`codigoFuncionario`),
  UNIQUE KEY `uk_funcionario_cpf` (`cpf`),
  KEY `fk_funcionario_cargo` (`codigoCargo`),
  KEY `fk_funcionario_cidade` (`codigoCidade`),
  CONSTRAINT `fk_funcionario_cargo` FOREIGN KEY (`codigoCargo`) REFERENCES `cargo` (`codigoCargo`),
  CONSTRAINT `fk_funcionario_cidade` FOREIGN KEY (`codigoCidade`) REFERENCES `cidade` (`codigoCidade`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela farmacia.medicamento
DROP TABLE IF EXISTS `medicamento`;
CREATE TABLE IF NOT EXISTS `medicamento` (
  `codigoMedicamento` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `descricao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `precoVenda` decimal(10,2) NOT NULL,
  `quantidadeEstoque` int NOT NULL,
  `codigoCategoriaMedicamento` int NOT NULL,
  `cnpjFabricante` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `codigoFornecedor` int NOT NULL,
  PRIMARY KEY (`codigoMedicamento`),
  KEY `fk_medicamento_categoria` (`codigoCategoriaMedicamento`),
  KEY `fk_medicamento_fabricante` (`cnpjFabricante`),
  KEY `fk_medicamento_fornecedor` (`codigoFornecedor`),
  CONSTRAINT `fk_medicamento_categoria` FOREIGN KEY (`codigoCategoriaMedicamento`) REFERENCES `categoriamedicamento` (`codigoCategoriaMedicamento`),
  CONSTRAINT `fk_medicamento_fabricante` FOREIGN KEY (`cnpjFabricante`) REFERENCES `fabricante` (`cnpj`),
  CONSTRAINT `fk_medicamento_fornecedor` FOREIGN KEY (`codigoFornecedor`) REFERENCES `fornecedor` (`codigoFornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Exportação de dados foi desmarcado.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
