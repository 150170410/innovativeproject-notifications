-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Wersja serwera:               5.7.16 - MySQL Community Server (GPL)
-- Serwer OS:                    Linux
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Zrzut struktury bazy danych notifications
CREATE DATABASE IF NOT EXISTS `notifications` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `notifications`;

-- Zrzut struktury tabela notifications.groups
CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Zrzucanie danych dla tabeli notifications.groups: ~3 rows (około)
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` (`group_id`, `name`) VALUES
	(1, 'first'),
	(2, 'second'),
	(3, 'third');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;

-- Zrzut struktury tabela notifications.messages
CREATE TABLE IF NOT EXISTS `messages` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `source_user_id` int(10) unsigned DEFAULT NULL,
  `target_user_id` int(10) unsigned DEFAULT NULL,
  `target_group_id` int(10) unsigned DEFAULT NULL,
  `priority` int(3) unsigned DEFAULT NULL,
  `value` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`msg_id`),
  KEY `FK_messages_users` (`target_user_id`),
  KEY `FK_messages_users_2` (`source_user_id`),
  KEY `FK_messages_groups` (`target_group_id`),
  CONSTRAINT `FK_messages_groups` FOREIGN KEY (`target_group_id`) REFERENCES `groups` (`group_id`),
  CONSTRAINT `FK_messages_users` FOREIGN KEY (`target_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_messages_users_2` FOREIGN KEY (`source_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='msg_id - id wiadomosci\r\ntimestamp - czas wyslania wiadomosci\r\nsource_user_id - zrodlo wiadomosci (user)\r\ntarget_user_id - cel wiadomosci (prywatna)\r\ntarget_group_id - cel wiadomosci (do grupy)\r\npriority - waznosc wiadomosci\r\nvalue - tresc wiadomosci';

-- Zrzucanie danych dla tabeli notifications.messages: ~2 rows (około)
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` (`msg_id`, `timestamp`, `source_user_id`, `target_user_id`, `target_group_id`, `priority`, `value`) VALUES
	(1, '2000-01-01 00:00:01', 1, 2, NULL, 1, '{"msg": "Siema ele elo"}'),
	(2, '2000-01-01 00:00:01', 2, 1, NULL, 1, '{"msg": "no siema"}'),
	(3, '2000-01-01 00:00:01', 1, NULL, 2, 2, '{"msg": "witam państwa na konferencji przedstawiam nowy s"}');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;

-- Zrzut struktury tabela notifications.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `mail` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `role` enum('P','U','PU') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Zrzucanie danych dla tabeli notifications.users: ~2 rows (około)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `login`, `pass`, `mail`, `role`) VALUES
	(1, 'test1', 'test', 'tes1@tes.com', 'P'),
	(2, 'test2', 'test', 'tes2@tes.com', 'P');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Zrzut struktury tabela notifications.users_groups
CREATE TABLE IF NOT EXISTS `users_groups` (
  `user_id` int(10) unsigned NOT NULL,
  `group_id` int(10) unsigned NOT NULL,
  KEY `FK__users` (`user_id`),
  KEY `FK__groups` (`group_id`),
  CONSTRAINT `FK__groups` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`),
  CONSTRAINT `FK__users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Zrzucanie danych dla tabeli notifications.users_groups: ~4 rows (około)
/*!40000 ALTER TABLE `users_groups` DISABLE KEYS */;
INSERT INTO `users_groups` (`user_id`, `group_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 3),
	(2, 2);
/*!40000 ALTER TABLE `users_groups` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
