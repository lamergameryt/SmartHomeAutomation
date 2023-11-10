# ************************************************************
# Antares - SQL Client
# Version 0.7.19
#
# https://antares-sql.app/
# https://github.com/antares-sql/antares
#
# Host: 127.0.0.1 ((Ubuntu) 8.0.35)
# Database: smart_home
# Generation time: 2023-11-10T20:10:55+05:30
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table bedroom_pins
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bedroom_pins`;

CREATE TABLE `bedroom_pins` (
                                `id` int NOT NULL,
                                `name` varchar(64) DEFAULT NULL,
                                `pin_num` int DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table bedroom_settings
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bedroom_settings`;

CREATE TABLE `bedroom_settings` (
                                    `user_id` int DEFAULT NULL,
                                    `pin_id` int DEFAULT NULL,
                                    `value` int DEFAULT NULL,
                                    KEY `user_id` (`user_id`),
                                    KEY `pin_id` (`pin_id`),
                                    CONSTRAINT `bedroom_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                    CONSTRAINT `bedroom_settings_ibfk_2` FOREIGN KEY (`pin_id`) REFERENCES `bedroom_pins` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table hall_logs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `hall_logs`;

CREATE TABLE `hall_logs` (
                             `logid` int NOT NULL AUTO_INCREMENT,
                             `userid` int DEFAULT NULL,
                             `pinid` int DEFAULT NULL,
                             `action` text,
                             `time` timestamp NULL DEFAULT NULL,
                             PRIMARY KEY (`logid`),
                             KEY `f_uid` (`userid`),
                             KEY `f_pid_fk` (`pinid`),
                             CONSTRAINT `f_pid_fk` FOREIGN KEY (`pinid`) REFERENCES `hall_pins` (`id`),
                             CONSTRAINT `f_uid` FOREIGN KEY (`userid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table hall_pins
# ------------------------------------------------------------

DROP TABLE IF EXISTS `hall_pins`;

CREATE TABLE `hall_pins` (
                             `id` int NOT NULL,
                             `name` varchar(64) DEFAULT NULL,
                             `pin_num` int DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table hall_settings
# ------------------------------------------------------------

DROP TABLE IF EXISTS `hall_settings`;

CREATE TABLE `hall_settings` (
                                 `user_id` int DEFAULT NULL,
                                 `pin_id` int DEFAULT NULL,
                                 `value` int DEFAULT NULL,
                                 KEY `user_id` (`user_id`),
                                 KEY `pin_id` (`pin_id`),
                                 CONSTRAINT `hall_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                 CONSTRAINT `hall_settings_ibfk_2` FOREIGN KEY (`pin_id`) REFERENCES `hall_pins` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table kitchen_logs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `kitchen_logs`;

CREATE TABLE `kitchen_logs` (
                                `logid` int NOT NULL AUTO_INCREMENT,
                                `userid` int DEFAULT NULL,
                                `pinid` int DEFAULT NULL,
                                `action` text,
                                `time` timestamp NULL DEFAULT NULL,
                                PRIMARY KEY (`logid`),
                                KEY `user_id_fk` (`userid`),
                                KEY `pin_id_fk` (`pinid`),
                                CONSTRAINT `pin_id_fk` FOREIGN KEY (`pinid`) REFERENCES `kitchen_pins` (`id`),
                                CONSTRAINT `user_id_fk` FOREIGN KEY (`userid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table kitchen_pins
# ------------------------------------------------------------

DROP TABLE IF EXISTS `kitchen_pins`;

CREATE TABLE `kitchen_pins` (
                                `id` int NOT NULL,
                                `name` varchar(64) DEFAULT NULL,
                                `pin_num` int DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table kitchen_settings
# ------------------------------------------------------------

DROP TABLE IF EXISTS `kitchen_settings`;

CREATE TABLE `kitchen_settings` (
                                    `user_id` int DEFAULT NULL,
                                    `pin_id` int DEFAULT NULL,
                                    `value` int DEFAULT NULL,
                                    KEY `user_id` (`user_id`),
                                    KEY `pin_id` (`pin_id`),
                                    CONSTRAINT `kitchen_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                    CONSTRAINT `kitchen_settings_ibfk_2` FOREIGN KEY (`pin_id`) REFERENCES `kitchen_pins` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table parameters
# ------------------------------------------------------------

DROP TABLE IF EXISTS `parameters`;

CREATE TABLE `parameters` (
                              `user_id` int DEFAULT NULL,
                              `encoding` varbinary(2048) DEFAULT NULL,
                              KEY `user_id` (`user_id`),
                              CONSTRAINT `parameters_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(64) DEFAULT NULL,
                         `day` int DEFAULT NULL,
                         `month` int DEFAULT NULL,
                         `year` int DEFAULT NULL,
                         `priority` int DEFAULT '-1',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of views
# ------------------------------------------------------------

# Creating temporary tables to overcome VIEW dependency errors
CREATE TABLE `bu_settings`(
                              `user_name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                              `pin_name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                              `pin_number` INT(10)   NULL  ,
                              `value` INT(10)   NULL
);

CREATE TABLE `hu_settings`(
                              `user_name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                              `pin_name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                              `pin_number` INT(10)   NULL  ,
                              `value` INT(10)   NULL
);

CREATE TABLE `ku_settings`(
                              `user_name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                              `pin_name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                              `pin_number` INT(10)   NULL  ,
                              `value` INT(10)   NULL
);

CREATE TABLE `user_parameters`(
                                  `id` INT(10)   NOT NULL  ,
                                  `name` VARCHAR(64)   NULL  COLLATE utf8mb4_0900_ai_ci,
                                  `priority` INT(10)   NULL  ,
                                  `encoding` VARBINARY(2048)   NULL
);

DROP TABLE IF EXISTS `bu_settings`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `bu_settings` AS select `u`.`name` AS `user_name`,`p`.`name` AS `pin_name`,`p`.`pin_num` AS `pin_number`,`b`.`value` AS `value` from ((`bedroom_settings` `b` join `users` `u` on((`u`.`id` = `b`.`user_id`))) join `bedroom_pins` `p` on((`p`.`id` = `b`.`pin_id`)));

DROP TABLE IF EXISTS `hu_settings`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `hu_settings` AS select `u`.`name` AS `user_name`,`p`.`name` AS `pin_name`,`p`.`pin_num` AS `pin_number`,`h`.`value` AS `value` from ((`hall_settings` `h` join `users` `u` on((`u`.`id` = `h`.`user_id`))) join `hall_pins` `p` on((`p`.`id` = `h`.`pin_id`)));

DROP TABLE IF EXISTS `ku_settings`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ku_settings` AS select `u`.`name` AS `user_name`,`p`.`name` AS `pin_name`,`p`.`pin_num` AS `pin_number`,`h`.`value` AS `value` from ((`kitchen_settings` `h` join `users` `u` on((`u`.`id` = `h`.`user_id`))) join `kitchen_pins` `p` on((`p`.`id` = `h`.`pin_id`)));

DROP TABLE IF EXISTS `user_parameters`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_parameters` AS select `u`.`id` AS `id`,`u`.`name` AS `name`,`u`.`priority` AS `priority`,`p`.`encoding` AS `encoding` from (`users` `u` join `parameters` `p` on((`u`.`id` = `p`.`user_id`)));



# Dump of triggers
# ------------------------------------------------------------

/*!50003 SET @OLD_SQL_MODE=@@SQL_MODE*/;;
/*!50003 SET SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `halllogs_insert` AFTER INSERT ON `hall_settings` FOR EACH ROW BEGIN
    DECLARE action_message TEXT;
    DECLARE pid INT;
    DECLARE uid INT;
    DECLARE new_value INT;

    SELECT NEW.pin_id, NEW.user_id, NEW.value INTO pid, uid, new_value;

    SET action_message = CONCAT('User with user id ', uid, ' has entered the value for pin ', pid, ' and the value is ',  new_value);

    INSERT INTO hall_logs (pinid, userid, action, time) VALUES (pid, uid, action_message, NOW());
END*/;;
DELIMITER ;
/*!50003 SET SQL_MODE=@OLD_SQL_MODE */;

/*!50003 SET @OLD_SQL_MODE=@@SQL_MODE*/;;
/*!50003 SET SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `halllogs_update` AFTER UPDATE ON `hall_settings` FOR EACH ROW BEGIN
    DECLARE action_message TEXT;
    DECLARE pid INT;
    DECLARE uid INT;
    DECLARE old_value INT;
    DECLARE new_value INT;

    SELECT NEW.pin_id, NEW.user_id, OLD.value, NEW.value INTO pid, uid, old_value, new_value;

    SET action_message = CONCAT('User with user id ', uid, ' has updated the value of pin ', pid, ' from ', old_value, ' to ', new_value);

    INSERT INTO hall_logs (pinid, userid, action, time) VALUES (pid, uid, action_message, NOW());
END*/;;
DELIMITER ;
/*!50003 SET SQL_MODE=@OLD_SQL_MODE */;

/*!50003 SET @OLD_SQL_MODE=@@SQL_MODE*/;;
/*!50003 SET SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `klogs_insert` AFTER INSERT ON `kitchen_settings` FOR EACH ROW BEGIN
    DECLARE action_message TEXT;
    DECLARE pid INT;
    DECLARE uid INT;
    DECLARE new_value INT;

    SELECT NEW.pin_id, NEW.user_id, NEW.value INTO pid, uid, new_value;
    SET action_message = CONCAT('User with user id ', uid, ' has entered the value for pin ', pid, ' and the value is ', new_value);

    INSERT INTO kitchen_logs (pinid, userid, action, time) VALUES (pid, uid, action_message, NOW());
END*/;;
DELIMITER ;
/*!50003 SET SQL_MODE=@OLD_SQL_MODE */;

/*!50003 SET @OLD_SQL_MODE=@@SQL_MODE*/;;
/*!50003 SET SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `klog_update` AFTER UPDATE ON `kitchen_settings` FOR EACH ROW BEGIN
DECLARE action_message TEXT;
DECLARE pid INT;
DECLARE uid INT;
DECLARE old_value INT;
DECLARE new_value INT;
SELECT NEW.pin_id, NEW.user_id, OLD.value, NEW.value INTO pid, uid, old_value, new_value;
SET action_message = CONCAT('User with user id ', uid, ' has updated the value of pin ', pid, ' from ', old_value, ' to ', new_value);
INSERT INTO kitchen_logs (pinid, userid, action, time) VALUES (pid, uid, action_message, NOW());
END*/;;
DELIMITER ;
/*!50003 SET SQL_MODE=@OLD_SQL_MODE */;



# Dump of routines
# ------------------------------------------------------------

/*!50003 DROP PROCEDURE IF EXISTS findKitchenSettings*/;;
/*!50003 SET @OLD_SQL_MODE=@@SQL_MODE*/;;
/*!50003 SET SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION"*/;;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `findKitchenSettings`(userId int)
BEGIN
SELECT * FROM ku_settings WHERE user_name=(SELECT name FROM users WHERE id = userId);
END*/;;
DELIMITER ;
/*!50003 SET SQL_MODE=@OLD_SQL_MODE*/;
/*!50003 DROP PROCEDURE IF EXISTS selectUsers*/;;
/*!50003 SET @OLD_SQL_MODE=@@SQL_MODE*/;;
/*!50003 SET SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION"*/;;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `selectUsers`(selectedYear INT)
BEGIN
SELECT * FROM users
WHERE year = selectedYear;
END*/;;
DELIMITER ;
/*!50003 SET SQL_MODE=@OLD_SQL_MODE*/;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
