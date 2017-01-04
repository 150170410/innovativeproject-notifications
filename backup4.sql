-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas generowania: 04 Sty 2017, 18:32
-- Wersja serwera: 5.7.16-0ubuntu0.16.04.1
-- Wersja PHP: 7.0.8-0ubuntu0.16.04.3

Create database if not exists notifications;
use notifications;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `notifications`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `messages`
--

CREATE TABLE `messages` (
  `msg_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `source_user_id` int(10) UNSIGNED DEFAULT NULL,
  `target_user_id` int(10) UNSIGNED DEFAULT NULL,
  `topic_id` int(10) UNSIGNED DEFAULT NULL,
  `priority` int(3) UNSIGNED DEFAULT NULL,
  `value` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `agg_type` int(2) UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='msg_id - id wiadomosci\r\ntimestamp - czas wyslania wiadomosci\r\nsource_user_id - zrodlo wiadomosci (user)\r\ntarget_user_id - cel wiadomosci (prywatna)\r\ntarget_group_id - cel wiadomosci (do grupy)\r\npriority - waznosc wiadomosci\r\nvalue - tresc wiadomosci\r\nagg_type - typ agregacji (indeks)';

--
-- Zrzut danych tabeli `messages`
--

INSERT INTO `messages` (`msg_id`, `timestamp`, `source_user_id`, `target_user_id`, `topic_id`, `priority`, `value`, `agg_type`) VALUES
(1, '1999-12-31 23:00:01', 1, 2, NULL, 1, '{"msg": "Siema ele elo"}', 0),
(2, '1999-12-31 23:00:01', 2, 1, NULL, 1, '{"msg": "Hello world2"}', 0),
(3, '1999-12-31 23:00:01', 1, NULL, 2, 2, '{"msg": "witam państwa na konferencji przedstawiam nowy s"}', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `subscriptions`
--

CREATE TABLE `subscriptions` (
  `target_user_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `subscriptions`
--

INSERT INTO `subscriptions` (`target_user_id`, `topic_id`) VALUES
(1, 1),
(1, 2),
(2, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `topics`
--

CREATE TABLE `topics` (
  `topic_id` int(11) NOT NULL,
  `source_user_id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `topics`
--

INSERT INTO `topics` (`topic_id`, `source_user_id`, `name`) VALUES
(1, 1, 'error'),
(2, 2, 'finished');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `login` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `mail` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `role` enum('P','U','PU') COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='tabela zawiera dane uzytkownikow\r\nuser_id - unikalny numer uzytkownika\r\nlogin - login uzytkownika\r\npass - haslo uzytkownika\r\nmail - adres email uzytkownika\r\nrole - rola uzytkownika (P - producent, U - uzytkownik, PU - producent i uzytkownik)';

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`user_id`, `login`, `pass`, `mail`, `role`) VALUES
(1, 'test1', 'test', 'tes1@tes.com', 'P'),
(2, 'test2', 'test', 'tes2@tes.com', 'P');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`msg_id`),
  ADD KEY `FK_msg_groups` (`topic_id`),
  ADD KEY `FK_msg_users` (`target_user_id`),
  ADD KEY `FK_msg_users_2` (`source_user_id`),
  ADD KEY `FK_msg_agg_type` (`agg_type`);

--
-- Indexes for table `topics`
--
ALTER TABLE `topics`
  ADD PRIMARY KEY (`topic_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `messages`
--
ALTER TABLE `messages`
  MODIFY `msg_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT dla tabeli `topics`
--
ALTER TABLE `topics`
  MODIFY `topic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `FK_msg_groups` FOREIGN KEY (`topic_id`) REFERENCES `groups` (`group_id`),
  ADD CONSTRAINT `FK_msg_users` FOREIGN KEY (`target_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `FK_msg_users_2` FOREIGN KEY (`source_user_id`) REFERENCES `users` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
