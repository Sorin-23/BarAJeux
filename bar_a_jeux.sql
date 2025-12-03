-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : mer. 03 déc. 2025 à 11:08
-- Version du serveur : 8.0.40
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bar_a_jeux`
--

-- --------------------------------------------------------

--
-- Structure de la table `avis_client`
--

CREATE TABLE `avis_client` (
  `id` int NOT NULL,
  `commentaire` varchar(200) DEFAULT NULL,
  `note` int NOT NULL,
  `titre` varchar(20) NOT NULL,
  `reservation` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `avis_client`
--

INSERT INTO `avis_client` (`id`, `commentaire`, `note`, `titre`, `reservation`) VALUES
(1, 'Super ambiance, le game master était top !', 5, 'Excellent', 2),
(2, 'Le jeu Azul est magnifique, très bonne découverte.', 5, 'Très beau jeu', 3),
(3, 'Un peu bruyant ce soir là, mais le service est impeccable.', 3, 'Sympa', 4),
(4, '7 Wonders est complexe mais on nous a bien expliqué les règles.', 4, 'Bonne pédagogie', 5),
(5, 'On a sauvé le monde à Pandemic ! Table confortable.', 5, 'Génial', 6),
(6, 'Expérience à refaire très vite.', 4, 'Super', 2),
(7, 'Les cocktails sont bons aussi !', 5, 'Miam', 3),
(8, 'Pas trop aimé le jeu proposé, mais le lieu est cool.', 3, 'Mitigé', 4),
(9, 'Soirée parfaite entre amis.', 5, 'Parfait', 5),
(10, 'Accueil chaleureux.', 4, 'Bien', 6);

-- --------------------------------------------------------

--
-- Structure de la table `badge`
--

CREATE TABLE `badge` (
  `id` int NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `nom_badge` varchar(30) NOT NULL,
  `point_min` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `badge`
--

INSERT INTO `badge` (`id`, `img_url`, `nom_badge`, `point_min`) VALUES
(1, 'novice.png', 'Novice', 0),
(2, 'apprenti.png', 'Apprenti', 50),
(3, 'expert.png', 'Expert', 150),
(4, 'maitre.png', 'Maître', 300),
(5, 'legende.png', 'Légende', 500);

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `type_compte` enum('Client','Employe') NOT NULL,
  `id` int NOT NULL,
  `mail` varchar(100) NOT NULL,
  `mot_de_passe` varchar(120) NOT NULL,
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `numero_telephone` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `code_postale` varchar(255) DEFAULT NULL,
  `date_creation` timestamp NULL DEFAULT NULL,
  `date_derniere_connexion` timestamp NULL DEFAULT NULL,
  `date_derniere_reservation` timestamp NULL DEFAULT NULL,
  `point_fidelite` int DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  `job` varchar(20) DEFAULT NULL,
  `game_master` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`type_compte`, `id`, `mail`, `mot_de_passe`, `nom`, `prenom`, `numero_telephone`, `adresse`, `code_postale`, `date_creation`, `date_derniere_connexion`, `date_derniere_reservation`, `point_fidelite`, `ville`, `job`, `game_master`) VALUES
('Employe', 1, 'employe@mail.com', '$2a$10$LrH2GFVU510Wyv6sYqo0WuG8hNTpQPFy3wykVAdtNm5sKE6SbOtWW', 'Dupont', 'Jean', '0600000000', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Serveur', b'1'),
('Client', 2, 'client@mail.com', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'Martin', 'Lucie', '0700000000', 'adress', 'codeP', '2025-10-12 22:00:00', '2025-12-03 10:53:44', NULL, 0, 'ville', NULL, NULL),
('Employe', 3, 'emp@mail.com', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'jonah', 'jonah', '0600000000', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'serveur', b'0'),
('Employe', 4, 'marie.durand@mail.com', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'Durand', 'Marie', '0601020304', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Responsable', b'1'),
('Client', 5, 'thomas.dubois@mail.com', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'Dubois', 'Thomas', NULL, NULL, NULL, '2025-01-09 23:00:00', NULL, NULL, 120, 'Paris', NULL, NULL),
('Client', 6, 'emma.roux@mail.com', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'Roux', 'Emma', NULL, NULL, NULL, '2025-02-14 23:00:00', NULL, NULL, 30, 'Lyon', NULL, NULL),
('Client', 7, 'lucas.blanc@mail.com', '$2a$10$Gt8bZj.gYP0uqKHFJZoXWu35VznwJrpd78B4z3aTyCa0SYOPFMRsC', 'Blanc', 'Lucas', NULL, NULL, NULL, '2025-03-19 23:00:00', NULL, NULL, 10, 'Marseille', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `emprunt`
--

CREATE TABLE `emprunt` (
  `id` int NOT NULL,
  `date_emprunt` date NOT NULL,
  `date_retour` date NOT NULL,
  `date_retour_reel` date DEFAULT NULL,
  `statut_location` enum('rendu','enCours','enRetard','annulé') NOT NULL,
  `client` int NOT NULL,
  `jeu` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `emprunt`
--

INSERT INTO `emprunt` (`id`, `date_emprunt`, `date_retour`, `date_retour_reel`, `statut_location`, `client`, `jeu`) VALUES
(1, '2025-10-13', '2025-10-28', NULL, 'annulé', 2, 1),
(13, '2025-01-10', '2025-01-24', '2025-01-24', 'rendu', 2, 1),
(14, '2025-03-05', '2025-03-19', '2025-03-20', 'rendu', 5, 3),
(15, '2025-06-15', '2025-06-29', '2025-06-29', 'rendu', 6, 2),
(16, '2025-09-01', '2025-09-15', '2025-09-14', 'rendu', 7, 4),
(17, '2025-10-01', '2025-10-15', NULL, 'enRetard', 2, 5),
(18, '2025-11-20', '2025-12-04', '2025-12-17', 'enRetard', 5, 1),
(19, '2025-11-25', '2025-12-09', NULL, 'enCours', 6, 3),
(20, '2025-12-01', '2025-12-15', NULL, 'enCours', 7, 2),
(21, '2026-01-15', '2026-01-29', NULL, 'enCours', 2, 4),
(22, '2026-02-20', '2026-03-06', NULL, 'enCours', 5, 5),
(23, '2026-03-10', '2026-03-24', NULL, 'annulé', 6, 1),
(24, '2025-12-16', '2025-12-31', NULL, 'enCours', 2, 1),
(25, '2025-12-04', '2025-12-19', NULL, 'enCours', 2, 3);

-- --------------------------------------------------------

--
-- Structure de la table `jeu`
--

CREATE TABLE `jeu` (
  `id` int NOT NULL,
  `age_minimum` int DEFAULT NULL,
  `besoin_game_master` bit(1) DEFAULT NULL,
  `durée` int DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `nombre_exemplaire` int DEFAULT NULL,
  `nombre_joueur_maximum` int DEFAULT NULL,
  `nombre_joueur_minimum` int DEFAULT NULL,
  `nom_jeu` varchar(50) NOT NULL,
  `note` decimal(2,1) DEFAULT NULL
) ;

--
-- Déchargement des données de la table `jeu`
--

INSERT INTO `jeu` (`id`, `age_minimum`, `besoin_game_master`, `durée`, `img_url`, `nombre_exemplaire`, `nombre_joueur_maximum`, `nombre_joueur_minimum`, `nom_jeu`, `note`) VALUES
(1, 10, b'0', 90, 'catan.jpg', 5, 4, 3, 'Catan', 4.5),
(2, 10, b'0', 30, 'loupgarou.jpg', 3, 18, 6, 'Loup-Garou', 4.0),
(3, 10, b'0', 45, 'pandemic.jpg', 4, 4, 2, 'Pandemic', 4.5),
(4, 10, b'0', 30, '7wonders.jpg', 5, 7, 3, '7 Wonders', 4.8),
(5, 8, b'0', 45, 'azul.jpg', 3, 4, 2, 'Azul', 4.6);

-- --------------------------------------------------------

--
-- Structure de la table `jeu_categories`
--

CREATE TABLE `jeu_categories` (
  `jeu_id` int NOT NULL,
  `categorie` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `jeu_categories`
--

INSERT INTO `jeu_categories` (`jeu_id`, `categorie`) VALUES
(1, 'FAMILIAL'),
(1, 'STRATEGIE'),
(2, 'AMBIANCE');

-- --------------------------------------------------------

--
-- Structure de la table `jeu_types`
--

CREATE TABLE `jeu_types` (
  `jeu_id` int NOT NULL,
  `type_jeu` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `jeu_types`
--

INSERT INTO `jeu_types` (`jeu_id`, `type_jeu`) VALUES
(1, 'COOPERATIF'),
(1, 'PLATEAU'),
(2, 'CARTES'),
(2, 'NARRATIF');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id` int NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime NOT NULL,
  `nombre_joueur` int DEFAULT NULL,
  `statut_reservation` enum('terminée','annulée','confirmée') CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `client` int NOT NULL,
  `game_master` int DEFAULT NULL,
  `jeu` int NOT NULL,
  `table_jeu` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id`, `date_debut`, `date_fin`, `nombre_joueur`, `statut_reservation`, `client`, `game_master`, `jeu`, `table_jeu`) VALUES
(2, '2025-01-10 14:00:00', '2025-01-10 16:00:00', 4, 'terminée', 2, 1, 1, 2),
(3, '2025-02-14 18:00:00', '2025-02-14 20:00:00', 2, 'terminée', 2, 1, 5, 4),
(4, '2025-03-20 19:00:00', '2025-03-20 21:00:00', 5, 'terminée', 5, 1, 2, 5),
(5, '2025-05-05 14:00:00', '2025-05-05 16:00:00', 3, 'terminée', 6, 1, 4, 3),
(6, '2025-06-12 20:00:00', '2025-06-12 22:00:00', 4, 'terminée', 7, 1, 3, 6),
(8, '2025-12-30 10:00:00', '2025-12-30 13:00:00', 4, 'confirmée', 2, 1, 1, 2),
(10, '2025-12-11 10:10:00', '2025-12-11 12:10:00', 3, NULL, 2, 1, 1, 3),
(12, '2025-12-15 10:10:00', '2025-12-15 12:10:00', 4, 'terminée', 2, NULL, 1, 5),
(13, '2025-12-17 10:10:00', '2025-12-17 14:10:00', 5, 'confirmée', 2, NULL, 4, 2),
(14, '2025-12-13 10:10:00', '2025-12-13 12:10:00', 5, 'confirmée', 2, NULL, 4, 6),
(15, '2025-12-17 11:00:00', '2025-12-17 12:00:00', 5, 'confirmée', 2, NULL, 4, 5),
(16, '2026-01-03 12:01:00', '2026-01-03 14:01:00', 4, 'confirmée', 2, NULL, 1, 2),
(17, '2025-12-29 12:02:00', '2025-12-29 14:02:00', 4, 'confirmée', 2, NULL, 3, 2),
(18, '2025-12-11 11:03:00', '2025-12-11 12:03:00', 4, 'confirmée', 2, NULL, 5, 2),
(19, '2026-01-20 13:59:00', '2026-01-20 15:59:00', 3, 'confirmée', 2, NULL, 5, 2),
(20, '2025-12-04 13:10:00', '2025-12-04 15:10:00', 4, 'confirmée', 2, NULL, 1, 2),
(21, '2025-12-25 15:01:00', '2025-12-25 16:01:00', 4, 'confirmée', 2, 4, 4, 3),
(22, '2025-12-06 17:09:00', '2025-12-06 18:09:00', 4, 'confirmée', 2, 4, 5, 2),
(23, '2025-12-13 16:27:00', '2025-12-13 17:27:00', 4, 'confirmée', 2, 4, 3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `table_jeu`
--

CREATE TABLE `table_jeu` (
  `id` int NOT NULL,
  `capacite` int NOT NULL,
  `nom_table` varchar(50) NOT NULL,
  `img_url` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `table_jeu`
--

INSERT INTO `table_jeu` (`id`, `capacite`, `nom_table`, `img_url`) VALUES
(2, 8, 'Le Trône du Jeu', 'tronedujeu.png'),
(3, 4, 'Table des Aventuriers', 'tabledesaventuriers.png'),
(4, 2, 'La Table des As', 'tabledesas.png'),
(5, 10, 'Le Comptoir Ludique', 'comptoirludique.png'),
(6, 6, 'Table du Stratège', 'tabledustratege.png'),
(7, 4, 'Le Cercle des Cartes', 'cercledescartes.png'),
(8, 8, 'La Salle des Dés', 'salledesdes.png'),
(9, 6, 'Table des Explorateurs', 'tabledesexplorateurs.png'),
(10, 10, 'Le Salon des Joueurs', 'salondesjoueurs.png'),
(11, 6, 'La Table du Roi', 'tableduroi.png');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `avis_client`
--
ALTER TABLE `avis_client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb7ju92rpq35dojdfqev8dd05s` (`reservation`);

--
-- Index pour la table `badge`
--
ALTER TABLE `badge`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sxkflw3mwf7n2eu6p49lnrjy0` (`mail`);

--
-- Index pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1iufefthoqj13m2u3dsw6nwjp` (`client`),
  ADD KEY `FKdrmus681t3m3hfqut988oh6qo` (`jeu`);

--
-- Index pour la table `jeu`
--
ALTER TABLE `jeu`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `jeu_categories`
--
ALTER TABLE `jeu_categories`
  ADD PRIMARY KEY (`jeu_id`,`categorie`);

--
-- Index pour la table `jeu_types`
--
ALTER TABLE `jeu_types`
  ADD PRIMARY KEY (`jeu_id`,`type_jeu`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqjn6d58d70uu5uwkfsyo1ghar` (`client`),
  ADD KEY `FK2furuysr5m7qr89fefdy628h1` (`game_master`),
  ADD KEY `FKmvkjvo365pwjtt9x7wkcxpfqk` (`jeu`),
  ADD KEY `FKchrwm3autjtflrbgkffam58q6` (`table_jeu`);

--
-- Index pour la table `table_jeu`
--
ALTER TABLE `table_jeu`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avis_client`
--
ALTER TABLE `avis_client`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `badge`
--
ALTER TABLE `badge`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `emprunt`
--
ALTER TABLE `emprunt`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `jeu`
--
ALTER TABLE `jeu`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pour la table `table_jeu`
--
ALTER TABLE `table_jeu`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `avis_client`
--
ALTER TABLE `avis_client`
  ADD CONSTRAINT `FKb7ju92rpq35dojdfqev8dd05s` FOREIGN KEY (`reservation`) REFERENCES `reservation` (`id`);

--
-- Contraintes pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `FK1iufefthoqj13m2u3dsw6nwjp` FOREIGN KEY (`client`) REFERENCES `compte` (`id`),
  ADD CONSTRAINT `FKdrmus681t3m3hfqut988oh6qo` FOREIGN KEY (`jeu`) REFERENCES `jeu` (`id`);

--
-- Contraintes pour la table `jeu_categories`
--
ALTER TABLE `jeu_categories`
  ADD CONSTRAINT `FKo61r98y6hee2tuuo5qw0ne8y4` FOREIGN KEY (`jeu_id`) REFERENCES `jeu` (`id`);

--
-- Contraintes pour la table `jeu_types`
--
ALTER TABLE `jeu_types`
  ADD CONSTRAINT `FK23etgcn5ow81mbttwrx321xy` FOREIGN KEY (`jeu_id`) REFERENCES `jeu` (`id`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK2furuysr5m7qr89fefdy628h1` FOREIGN KEY (`game_master`) REFERENCES `compte` (`id`),
  ADD CONSTRAINT `FKchrwm3autjtflrbgkffam58q6` FOREIGN KEY (`table_jeu`) REFERENCES `table_jeu` (`id`),
  ADD CONSTRAINT `FKmvkjvo365pwjtt9x7wkcxpfqk` FOREIGN KEY (`jeu`) REFERENCES `jeu` (`id`),
  ADD CONSTRAINT `FKqjn6d58d70uu5uwkfsyo1ghar` FOREIGN KEY (`client`) REFERENCES `compte` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
