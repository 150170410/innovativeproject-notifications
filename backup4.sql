-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Wersja serwera:               5.7.16 - MySQL Community Server (GPL)
-- Serwer OS:                    Linux
-- HeidiSQL Wersja:              9.4.0.5139
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

-- Zrzut struktury tabela notifications.msg
CREATE TABLE IF NOT EXISTS `msg` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `source_user_id` int(10) unsigned DEFAULT NULL,
  `target_user_id` int(10) unsigned DEFAULT NULL,
  `target_group_id` int(10) unsigned DEFAULT NULL,
  `priority` int(3) unsigned DEFAULT NULL,
  `value` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agg_type` int(2) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`msg_id`),
  KEY `FK_msg_groups` (`target_group_id`),
  KEY `FK_msg_users` (`target_user_id`),
  KEY `FK_msg_users_2` (`source_user_id`),
  KEY `FK_msg_agg_type` (`agg_type`),
  CONSTRAINT `FK_msg_groups` FOREIGN KEY (`target_group_id`) REFERENCES `groups` (`group_id`),
  CONSTRAINT `FK_msg_users` FOREIGN KEY (`target_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_msg_users_2` FOREIGN KEY (`source_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='msg_id - id wiadomosci\r\ntimestamp - czas wyslania wiadomosci\r\nsource_user_id - zrodlo wiadomosci (user)\r\ntarget_user_id - cel wiadomosci (prywatna)\r\ntarget_group_id - cel wiadomosci (do grupy)\r\npriority - waznosc wiadomosci\r\nvalue - tresc wiadomosci\r\nagg_type - typ agregacji (indeks)';

-- Zrzucanie danych dla tabeli notifications.msg: ~30 rows (około)
/*!40000 ALTER TABLE `msg` DISABLE KEYS */;
INSERT INTO `msg` (`msg_id`, `timestamp`, `source_user_id`, `target_user_id`, `target_group_id`, `priority`, `value`, `agg_type`) VALUES
	(1, '2000-01-01 00:00:01', 1, 2, NULL, 1, '{"msg": "Siema ele elo"}', 0),
	(2, '2000-01-01 00:00:01', 2, 1, NULL, 1, '{"msg": "Hello world2"}', 0),
	(3, '2000-01-01 00:00:01', 1, NULL, 2, 2, '{"msg": "witam państwa na konferencji przedstawiam nowy s"}', 0),
	(5, '2016-12-01 14:23:55', 1, 1, 1, 1, '14:23:55', 0),
	(6, '2016-12-01 14:45:12', 1, 1, 1, 1, '14:45:12', 0),
	(7, '2016-12-01 14:53:32', 1, 1, 1, 1, '14:53:32', 0),
	(8, '2016-12-01 14:53:38', 1, 1, 1, 1, '14:53:37', 0),
	(9, '2016-12-01 14:53:43', 1, 1, 1, 1, '14:53:42', 0),
	(10, '2016-12-01 14:53:48', 1, 1, 1, 1, '14:53:47', 0),
	(11, '2016-12-01 14:53:53', 1, 1, 1, 1, '14:53:52', 0),
	(12, '2016-12-01 14:53:58', 1, 1, 1, 1, '14:53:57', 0),
	(13, '2016-12-01 14:54:03', 1, 1, 1, 1, '14:54:02', 0),
	(14, '2016-12-01 14:54:08', 1, 1, 1, 1, '14:54:07', 0),
	(15, '2016-12-01 14:54:13', 1, 1, 1, 1, '14:54:13', 0),
	(16, '2016-12-01 16:15:06', 1, 1, 1, 1, '16:14:57', 0),
	(17, '2016-12-01 16:23:40', 1, 1, 1, 1, '16:23:31', 0),
	(18, '2016-12-01 16:23:46', 1, 1, 1, 1, '16:23:37', 0),
	(19, '2016-12-01 16:23:52', 1, 1, 1, 1, '16:23:42', 0),
	(20, '2016-12-01 16:23:57', 1, 1, 1, 1, '16:23:48', 0),
	(21, '2016-12-01 16:24:03', 1, 1, 1, 1, '16:23:53', 0),
	(22, '2016-12-01 16:24:10', 1, 1, 1, 1, '16:23:59', 0),
	(23, '2016-12-01 16:24:17', 1, 1, 1, 1, '16:24:06', 0),
	(24, '2016-12-01 16:24:23', 1, 1, 1, 1, '16:24:14', 0),
	(25, '2016-12-01 16:24:30', 1, 1, 1, 1, '16:24:19', 0),
	(26, '2016-12-01 16:24:37', 1, 1, 1, 1, '16:24:26', 0),
	(27, '2016-12-01 16:24:44', 1, 1, 1, 1, '16:24:34', 0),
	(28, '2016-12-01 16:24:50', 1, 1, 1, 1, '16:24:40', 0),
	(29, '2016-12-01 16:24:56', 1, 1, 1, 1, '16:24:46', 0),
	(30, '2016-12-01 16:25:02', 1, 1, 1, 1, '16:24:52', 0),
	(31, '2016-12-01 16:25:08', 1, 1, 1, 1, '16:24:58', 0);
/*!40000 ALTER TABLE `msg` ENABLE KEYS */;

-- Zrzut struktury tabela notifications.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `mail` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `role` enum('P','U','PU') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='tabela zawiera dane uzytkownikow\r\nuser_id - unikalny numer uzytkownika\r\nlogin - login uzytkownika\r\npass - haslo uzytkownika\r\nmail - adres email uzytkownika\r\nrole - rola uzytkownika (P - producent, U - uzytkownik, PU - producent i uzytkownik)';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='tabla laczy uzytkownikow i grupy\r\nuser_id - id uzytkownika z tabeli users\r\ngroup_id - id grupy z tabeli groups';

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
