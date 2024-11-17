-- MySQL Workbench Synchronization
-- Generated: 2024-11-18 00:29
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: HP

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `booking_api` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `booking_api`.`auth_user` (
  `id` BIGINT(19) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `gender` TINYINT(4) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `is_verified` TINYINT(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `booking_api`.`profile` (
  `url` VARCHAR(255) NOT NULL,
  `auth_user_id` BIGINT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`auth_user_id`, `url`),
  CONSTRAINT `fk_profile_auth_user`
    FOREIGN KEY (`auth_user_id`)
    REFERENCES `booking_api`.`auth_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `booking_api`.`package` (
  `id` BIGINT(19) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(8,2) NOT NULL,
  `credits` INT(11) NOT NULL,
  `valid_day` INT(11) NOT NULL,
  `country_id` TINYINT(3) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `country_id`),
  INDEX `fk_package_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_package_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `booking_api`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `booking_api`.`country` (
  `id` TINYINT(3) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `code` CHAR(3) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `booking_api`.`user_packages` (
  `auth_user_id` BIGINT(19) UNSIGNED NOT NULL,
  `package_id` BIGINT(19) UNSIGNED NOT NULL,
  `start_time` TIMESTAMP NOT NULL,
  `remaining_credits` INT(11) NOT NULL,
  PRIMARY KEY (`auth_user_id`, `package_id`),
  INDEX `fk_auth_user_has_package_package1_idx` (`package_id` ASC) VISIBLE,
  INDEX `fk_auth_user_has_package_auth_user1_idx` (`auth_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_auth_user_has_package_auth_user1`
    FOREIGN KEY (`auth_user_id`)
    REFERENCES `booking_api`.`auth_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auth_user_has_package_package1`
    FOREIGN KEY (`package_id`)
    REFERENCES `booking_api`.`package` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `booking_api`.`class` (
  `id` BIGINT(19) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `start_time` TIMESTAMP NOT NULL,
  `end_time` TIMESTAMP NOT NULL,
  `cost` INT(11) NOT NULL,
  `total_slot` INT(11) NOT NULL,
  `country_id` TINYINT(3) UNSIGNED NOT NULL,
  `classcol` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `country_id`),
  INDEX `fk_class_country1_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `fk_class_country1`
    FOREIGN KEY (`country_id`)
    REFERENCES `booking_api`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `booking_api`.`user_classes` (
  `auth_user_id` BIGINT(19) UNSIGNED NOT NULL,
  `class_id` BIGINT(19) UNSIGNED NOT NULL,
  PRIMARY KEY (`auth_user_id`, `class_id`),
  INDEX `fk_auth_user_has_class_class1_idx` (`class_id` ASC) VISIBLE,
  INDEX `fk_auth_user_has_class_auth_user1_idx` (`auth_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_auth_user_has_class_auth_user1`
    FOREIGN KEY (`auth_user_id`)
    REFERENCES `booking_api`.`auth_user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auth_user_has_class_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `booking_api`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
