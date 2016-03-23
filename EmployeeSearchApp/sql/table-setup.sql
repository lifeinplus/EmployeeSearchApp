create database if not exists demo;

use demo;

drop table if exists audit_history;
drop table if exists employees;
drop table if exists users;

CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(64) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `department` varchar(64) DEFAULT NULL,
  `salary` DECIMAL(10,2) DEFAULT NULL,
  `pdf_resume` BLOB,
  `txt_resume` LONGTEXT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(64) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `admin` tinyint DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `audit_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `action` varchar(128) DEFAULT NULL,
  `action_date_time` timestamp,
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id) REFERENCES  users(id),
  FOREIGN KEY (employee_id) REFERENCES  employees(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (1,'John','Doe','john.doe@gmail.com', 'HR', 55000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (2,'Mary','Public','mary.public@gmail.com', 'Engineering', 75000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (3,'Susan','Queue','susan.queue@gmail.com', 'Legal', 130000.00);

INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (4,'David','Williams','david.williams@gmail.com', 'HR', 120000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (5,'Lisa','Johnson','lisa.johnson@gmail.com', 'Engineering', 50000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (6,'Paul','Smith','paul.smith@gmail.com', 'Legal', 100000.00);

INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (7,'Carl','Adams','carl.adams@gmail.com', 'Engineering', 50000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (8,'Bill','Brown','bill.brown@gmail.com', 'Engineering', 50000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (9,'Susan','Thomas','susan.thomas@gmail.com', 'Legal', 80000.00);

INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (10,'John','Davis','john.davis@gmail.com', 'HR', 45000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (11,'Mary','Fowler','mary.fowler@gmail.com', 'Engineering', 65000.00);
INSERT INTO `employees` (`id`,`first_name`,`last_name`,`email`, `department`, `salary`) VALUES (12,'David','Waters','david.waters@gmail.com', 'Legal', 90000.00);

INSERT INTO `users` (`id`,`first_name`,`last_name`,`email`,`password`,`admin`) VALUES (1,'Joe','Alpha','joe.alpha@gmail.com','k4AXDX87vLHmzXC18klyPqt84vp6HlrPd+cnU4IggndUq8Vrikonz/pbdrXQUJlz',0);
INSERT INTO `users` (`id`,`first_name`,`last_name`,`email`,`password`,`admin`) VALUES (2,'Jane','Beta','jane.beta@gmail.com','X70IvzjITvoh76FV2gBXPdnt6hYr6KgUoTm+fJcrhlGWpNigYQRY8Ql/+lI/rcdx',0);
INSERT INTO `users` (`id`,`first_name`,`last_name`,`email`,`password`,`admin`) VALUES (3,'Becky','Zeta','becky.zeta@gmail.com','zO3IE+Yb23RpGiYWeSHkZzKVu0YXQ0/pY0omSWIyi1SexPuuEpdDxl2nlDerx2TM',0);
INSERT INTO `users` (`id`,`first_name`,`last_name`,`email`,`password`,`admin`) VALUES (4,'Charlie','Admin','charlie.admin@gmail.com','C9VcMJhFKQCiK9WHQ2cM0DxbuRbpHt2o5/K8078ci5XQGkGg/Zfv5HCpG6wGwrR1',1);
INSERT INTO `users` (`id`,`first_name`,`last_name`,`email`,`password`,`admin`) VALUES (5,'Percy','Miracles','percy.miracles@gmail.com','7LhDtE2xbCJHKcvH80XpSKUolWnlC0SokdX83ZiBfHSP7xHwTKm6KLwmxQeiXvvV',1);

--
-- DEFINE STORED PROCEDURES
-- 

DELIMITER $$
DROP PROCEDURE IF EXISTS `get_count_for_department`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_count_for_department`(IN the_department VARCHAR(64), OUT the_count INT)
BEGIN
	
	SELECT COUNT(*) INTO the_count FROM employees where department=the_department;

END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS `greet_the_department`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `greet_the_department`(INOUT department VARCHAR(64))
BEGIN

	SET department = concat('Hello to the awesome ', department, ' team!');

END$$
DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS `increase_salaries_for_department`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `increase_salaries_for_department`(IN the_department VARCHAR(64), IN increase_amount DECIMAL(10,2))
BEGIN

	UPDATE EMPLOYEES SET salary= salary + increase_amount where department=the_department;

END$$
DELIMITER ;
