SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Stand
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Stand` ;
USE `Stand`;
-- DROP DATABASE Stand;
-- -----------------------------------------------------
-- Table `Stand`.`Cliente`
-- -----------------------------------------------------
-- DROP TABLE Cliente;
-- DESC Cliente;
CREATE TABLE IF NOT EXISTS `Stand`.`Cliente` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Sexo` VARCHAR(15) NOT NULL,
  `DataNascimento` DATE NULL,
  `Rua` VARCHAR(45) NOT NULL,
  `Distrito` VARCHAR(20) NOT NULL,
  `Localidade` VARCHAR(20) NOT NULL,
  `Codigo_Postal` VARCHAR(8) NOT NULL,
  `Email` VARCHAR(45) NULL,
  `Contribuinte` INT NOT NULL,
  `ValorCompras` DECIMAL(10,2) NOT NULL,
  `Dívida` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`Telefone`
-- -----------------------------------------------------
-- DROP TABLE Telefone;
-- DESC Telefone;
CREATE TABLE IF NOT EXISTS `Stand`.`Telefone` (
  `Cliente_ID` INT NOT NULL,
  `Telefone` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`Cliente_ID`),
  CONSTRAINT `fk_Telefone_Cliente1`
    FOREIGN KEY (`Cliente_ID`)
    REFERENCES `Stand`.`Cliente` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`Funcionario`
-- -----------------------------------------------------
-- DROP TABLE Funcionario;
CREATE TABLE IF NOT EXISTS `Stand`.`Funcionario` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `DataNascimento` DATE NOT NULL,
  `Funcao` VARCHAR(30) NOT NULL,
  `Sexo` VARCHAR(15) NOT NULL,
  `Salário` DECIMAL(8,2) NOT NULL,
  `Telefone` INT NOT NULL,
  `DataContratacao` DATE NOT NULL,
  `Nvendas` INT NOT NULL,
  `Nalugueres` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`Aluguer`
-- -----------------------------------------------------
-- DROP TABLE Aluguer;
-- DESD Aluguer;
CREATE TABLE IF NOT EXISTS `Stand`.`Aluguer` (
  `Numero` INT NOT NULL AUTO_INCREMENT,
  `Funcionario_ID` INT NOT NULL,
  `Cliente_ID` INT NOT NULL,
  `PlanoPagamento` VARCHAR(45) NOT NULL,
  `DataEmissao` DATE NOT NULL,
  `Contribuinte` INT NOT NULL,
  `DataPagamento` DATE NOT NULL,
  `Valor` DECIMAL(8,2) NOT NULL,
  `FotoEstado` LONG NOT NULL,
  `DadosSeguro` VARCHAR(45) NOT NULL,
  `PeriodoAluguer` INT NOT NULL,
  PRIMARY KEY (`Numero`),
  INDEX `fk_Aluguer_Funcionario1_idx` (`Funcionario_ID` ASC) VISIBLE,
  INDEX `fk_Aluguer_Cliente1_idx` (`Cliente_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Aluguer_Funcionario1`
    FOREIGN KEY (`Funcionario_ID`)
    REFERENCES `Stand`.`Funcionario` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluguer_Cliente1`
    FOREIGN KEY (`Cliente_ID`)
    REFERENCES `Stand`.`Cliente` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`Compra`
-- -----------------------------------------------------
-- DROP TABLE Compra;
-- DESC Compra;
CREATE TABLE IF NOT EXISTS `Stand`.`Compra` (
  `Numero` INT NOT NULL AUTO_INCREMENT,
  `Cliente_ID` INT NOT NULL,
  `Funcionario_ID` INT NOT NULL,
  `PlanoPagamento` VARCHAR(45) NOT NULL,
  `DataEmissao` DATE NOT NULL,
  `Contribuinte` INT NOT NULL,
  `DataPagamento` DATE NOT NULL,
  `Valor` DECIMAL(9,2) NOT NULL,
  PRIMARY KEY (`Numero`),
  INDEX `fk_Compra_Cliente1_idx` (`Cliente_ID` ASC) VISIBLE,
  INDEX `fk_Compra_Funcionario1_idx` (`Funcionario_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Compra_Cliente1`
    FOREIGN KEY (`Cliente_ID`)
    REFERENCES `Stand`.`Cliente` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_Funcionario1`
    FOREIGN KEY (`Funcionario_ID`)
    REFERENCES `Stand`.`Funcionario` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`Carro`
-- -----------------------------------------------------
-- DROP TABLE Carro;
-- DESC Carro;
CREATE TABLE IF NOT EXISTS `Stand`.`Carro` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Destinacao` VARCHAR(10) NOT NULL,
  `Marca` VARCHAR(15) NOT NULL,
  `Modelo` VARCHAR(25) NOT NULL,
  `Kilometragem` INT NOT NULL,
  `AnoFabrico` VARCHAR(5) NOT NULL,
  `Preco` DECIMAL(10,2) NOT NULL,
  `Integridade` VARCHAR(45) NOT NULL,
  `Estado` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`VendaInclui`
-- -----------------------------------------------------
-- DROP TABLE VendaInclui;
-- DESC VendaInclui;
CREATE TABLE IF NOT EXISTS `Stand`.`VendaInclui` (
  `Carro_ID` INT NOT NULL,
  `Compra_Numero` INT NOT NULL,
  `Preco` DECIMAL(9,2) NOT NULL,
  `Imposto` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`Carro_ID`, `Compra_Numero`),
  INDEX `fk_VendaInclui_Compra1_idx` (`Compra_Numero` ASC) VISIBLE,
  CONSTRAINT `fk_VendaInclui_Carro1`
    FOREIGN KEY (`Carro_ID`)
    REFERENCES `Stand`.`Carro` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_VendaInclui_Compra1`
    FOREIGN KEY (`Compra_Numero`)
    REFERENCES `Stand`.`Compra` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`AluguerInclui`
-- -----------------------------------------------------
-- DROP TABLE AluguerInclui;
-- DESC AluguerInclui;
CREATE TABLE IF NOT EXISTS `Stand`.`AluguerInclui` (
  `Aluguer_Numero` INT NOT NULL,
  `Carro_ID` INT NOT NULL,
  `Preco` DECIMAL(8,2) NOT NULL,
  `Imposto` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`Carro_ID`, `Aluguer_Numero`),
  INDEX `fk_AluguerInclui_Aluguer1_idx` (`Aluguer_Numero` ASC) VISIBLE,
  CONSTRAINT `fk_AluguerInclui_Carro1`
    FOREIGN KEY (`Carro_ID`)
    REFERENCES `Stand`.`Carro` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AluguerInclui_Aluguer1`
    FOREIGN KEY (`Aluguer_Numero`)
    REFERENCES `Stand`.`Aluguer` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Stand`.`efetuaPedido`
-- -----------------------------------------------------
-- DROP TABLE efetuaPedido;
-- DESC efetuaPedido;
CREATE TABLE IF NOT EXISTS `Stand`.`efetuaPedido` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Data_pedido` DATE NOT NULL,
  `Compra_Numero` INT NULL,
  `Aluguer_Numero` INT NULL,
  `Funcionario_ID` INT NOT NULL,
  `Cliente_ID` INT NOT NULL,
  INDEX `fk_efetuaPedido_Compra1_idx` (`Compra_Numero` ASC) VISIBLE,
  INDEX `fk_efetuaPedido_Aluguer1_idx` (`Aluguer_Numero` ASC) VISIBLE,
  PRIMARY KEY (`ID`),
  INDEX `fk_efetuaPedido_Funcionario1_idx` (`Funcionario_ID` ASC) VISIBLE,
  INDEX `fk_efetuaPedido_Cliente1_idx` (`Cliente_ID` ASC) VISIBLE,
  CONSTRAINT `fk_efetuaPedido_Compra1`
    FOREIGN KEY (`Compra_Numero`)
    REFERENCES `Stand`.`Compra` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_efetuaPedido_Aluguer1`
    FOREIGN KEY (`Aluguer_Numero`)
    REFERENCES `Stand`.`Aluguer` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_efetuaPedido_Funcionario1`
    FOREIGN KEY (`Funcionario_ID`)
    REFERENCES `Stand`.`Funcionario` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_efetuaPedido_Cliente1`
    FOREIGN KEY (`Cliente_ID`)
    REFERENCES `Stand`.`Cliente` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
