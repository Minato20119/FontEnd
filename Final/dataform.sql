-- MySQL Script generated by MySQL Workbench
-- Fri Apr 20 10:05:37 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema html
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema html
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `html` DEFAULT CHARACTER SET utf8 ;
USE `html` ;

-- -----------------------------------------------------
-- Table `html`.`dataform`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `html`.`dataform` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `form1_fullname` VARCHAR(30) NULL DEFAULT NULL,
  `form2_sex` VARCHAR(5) NULL,
  `form3_birthday` DATE NULL DEFAULT NULL,
  `form4_Place_of_birth` VARCHAR(30) NULL DEFAULT NULL,
  `form5_Nationality_at_birth` VARCHAR(30) NULL DEFAULT NULL,
  `form6_Current_nationality` VARCHAR(30) NULL DEFAULT NULL,
  `form7_Religion` VARCHAR(30) NULL DEFAULT NULL,
  `form8_Occupation` VARCHAR(30) NULL DEFAULT NULL,
  `form9_Buiness_address` VARCHAR(30) NULL DEFAULT NULL,
  `form10_Permanent_residential_address` VARCHAR(30) NULL DEFAULT NULL,
  `form10_Tel_Email` VARCHAR(30) NULL DEFAULT NULL,
  `form11_Family_members` VARCHAR(600) NULL DEFAULT NULL,
  `form12_Passport` VARCHAR(30) NULL DEFAULT NULL,
  `form12_Type` VARCHAR(30) NULL DEFAULT NULL,
  `form12_Issuing_authority` VARCHAR(30) NULL DEFAULT NULL,
  `form12_day` DATE NULL DEFAULT NULL,
  `form13_day` DATE NULL DEFAULT NULL,
  `form14_day` DATE NULL DEFAULT NULL,
  `form14_stayday` VARCHAR(30) NULL DEFAULT NULL,
  `form15_Purpose_of_entry` VARCHAR(30) NULL DEFAULT NULL,
  `form16_address` VARCHAR(30) NULL DEFAULT NULL,
  `form17_organisation` VARCHAR(30) NULL DEFAULT NULL,
  `form17_address1` VARCHAR(30) NULL DEFAULT NULL,
  `form17_fullname` VARCHAR(30) NULL DEFAULT NULL,
  `form17_address2` VARCHAR(30) NULL DEFAULT NULL,
  `form17_relationship` VARCHAR(30) NULL DEFAULT NULL,
  `form18_Accompanying_children` VARCHAR(200) NULL DEFAULT NULL,
  `form19_Applying_visa` VARCHAR(30) NULL DEFAULT NULL,
  `form19_startDay` DATE NULL DEFAULT NULL,
  `form19_endDay` DATE NULL DEFAULT NULL,
  `form20_Other_requests` VARCHAR(30) NULL DEFAULT NULL,
  `form20_at` VARCHAR(30) NULL DEFAULT NULL,
  `form21_day` DATE NULL DEFAULT NULL,
  `image` VARCHAR(20000) NULL DEFAULT NULL,
  `startTime` DATE NULL DEFAULT NULL,
  `currentTime` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;