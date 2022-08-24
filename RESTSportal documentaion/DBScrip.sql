CREATE DATABASE `sportal_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE sportal_db;

CREATE TABLE `activity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bundle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bundle_activity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_bundle` int DEFAULT NULL,
  `id_activity` int DEFAULT NULL,
  `valid_until` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_bundle` (`id_bundle`),
  KEY `id_activity` (`id_activity`),
  CONSTRAINT `bundle_activity_ibfk_1` FOREIGN KEY (`id_bundle`) REFERENCES `bundle` (`id`),
  CONSTRAINT `bundle_activity_ibfk_2` FOREIGN KEY (`id_activity`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `enabled` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usernameUnique` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bundle_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_bundle` int DEFAULT NULL,
  `id_user` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_bundle` (`id_bundle`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `bundle_user_ibfk_1` FOREIGN KEY (`id_bundle`) REFERENCES `bundle` (`id`),
  CONSTRAINT `bundle_user_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `week` (
  `id` int NOT NULL AUTO_INCREMENT,
  `week_number` int DEFAULT NULL,
  `year_of_week` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_schedule` date DEFAULT NULL,
  `hour_schedule` time DEFAULT NULL,
  `total_reservations` int DEFAULT NULL,
  `available_reservations` int DEFAULT NULL,
  `id_activity` int DEFAULT NULL,
  `id_week` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_activity` (`id_activity`),
  KEY `id_week` (`id_week`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`id_activity`) REFERENCES `activity` (`id`),
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`id_week`) REFERENCES `week` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int DEFAULT NULL,
  `id_schedule` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_schedule` (`id_schedule`),
  CONSTRAINT `user_schedule_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  CONSTRAINT `user_schedule_ibfk_2` FOREIGN KEY (`id_schedule`) REFERENCES `schedule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int DEFAULT NULL,
  `id_schedule` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_UserReservation` (`id_user`),
  KEY `FK_ScheduleReservation` (`id_schedule`),
  CONSTRAINT `FK_ScheduleReservation` FOREIGN KEY (`id_schedule`) REFERENCES `schedule` (`id`),
  CONSTRAINT `FK_UserReservation` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
