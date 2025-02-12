use photo;

CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_info` (
  `birth` date DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `user_info_id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(15) DEFAULT NULL,
  `first_name` varchar(32) NOT NULL,
  `last_name` varchar(32) NOT NULL,
  `nickname` varchar(32) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `profile_image_path` varchar(255) DEFAULT NULL,
  `gender` enum('FEMALE','MALE') DEFAULT NULL,
  PRIMARY KEY (`user_info_id`),
  UNIQUE KEY `UKhixwjgx0ynne0cq4tqvoawoda` (`user_id`),
  UNIQUE KEY `UK8v67659wisb17ryqyrscjipg7` (`nickname`),
  CONSTRAINT `FKn8pl63y4abe7n0ls6topbqjh2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `follow` (
  `alarm` bit(1) NOT NULL,
  `follow_id` bigint NOT NULL AUTO_INCREMENT,
  `followee_id` bigint NOT NULL,
  `follower_id` bigint NOT NULL,
  PRIMARY KEY (`follow_id`),
  KEY `FKjhmtcmoxpgcojx2p3h7lcphsq` (`followee_id`),
  KEY `FKmow2qk674plvwyb4wqln37svv` (`follower_id`),
  CONSTRAINT `FKjhmtcmoxpgcojx2p3h7lcphsq` FOREIGN KEY (`followee_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKmow2qk674plvwyb4wqln37svv` FOREIGN KEY (`follower_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post` (
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  `region` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FK72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
  CONSTRAINT `FK72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_like` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpmmko3h7yonaqhy5gxvnmdeue` (`user_id`,`post_id`),
  KEY `FKj7iy0k7n3d0vkh8o7ibjna884` (`post_id`),
  CONSTRAINT `FKhuh7nn7libqf645su27ytx21m` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKj7iy0k7n3d0vkh8o7ibjna884` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment` (
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment_like` (
  `comment_id` bigint NOT NULL,
  `comment_like_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`comment_like_id`),
  KEY `FKqlv8phl1ibeh0efv4dbn3720p` (`comment_id`),
  KEY `FK6arwb0j7by23pw04ljdtxq4p5` (`user_id`),
  CONSTRAINT `FK6arwb0j7by23pw04ljdtxq4p5` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKqlv8phl1ibeh0efv4dbn3720p` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reply` (
  `comment_id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `reply_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `FK6w0ns67lrq1jdiwi5xvtj1vxx` (`comment_id`),
  KEY `FKapyyxlgntertu5okpkr685ir9` (`user_id`),
  CONSTRAINT `FK6w0ns67lrq1jdiwi5xvtj1vxx` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FKapyyxlgntertu5okpkr685ir9` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `reply_like` (
  `reply_id` bigint NOT NULL,
  `reply_like_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`reply_like_id`),
  KEY `FKqf4y6h9cfw6jtjrd04p1rpbbj` (`reply_id`),
  KEY `FKpxa8cdl9hhygcscm59i1u2vq3` (`user_id`),
  CONSTRAINT `FKpxa8cdl9hhygcscm59i1u2vq3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKqf4y6h9cfw6jtjrd04p1rpbbj` FOREIGN KEY (`reply_id`) REFERENCES `reply` (`reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `mail` (
  `saw` bit(1) NOT NULL DEFAULT b'0',
  `created_at` datetime(6) DEFAULT NULL,
  `mail_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`mail_id`),
  KEY `FK1dpf0qlamh28vjpo1ut0a5b8s` (`user_id`),
  CONSTRAINT `FK1dpf0qlamh28vjpo1ut0a5b8s` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
