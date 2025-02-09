create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

INSERT IGNORE INTO `users` VALUES ('user', '{noop}EazyBytes@12345', '1');
INSERT IGNORE INTO `authorities` VALUES ('user', 'read');

-- admin/admin123
INSERT IGNORE INTO `users` VALUES ('admin', '{bcrypt}$2a$12$355GP9HsjDsE8XbBhPndc.inh5wASqm4pt2HVmqo4HOHUrn5Mgm9C', '1');
INSERT IGNORE INTO `authorities` VALUES ('admin', 'admin');

CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT  INTO `customer` (`email`,`pwd`,`role`) VALUES ('user@sapo.pt', '{noop}EazyBytes@12345', 'read');
INSERT  INTO `customer` (`email`,`pwd`,`role`) VALUES ('admin@sapo.pt', '{bcrypt}$2a$12$355GP9HsjDsE8XbBhPndc.inh5wASqm4pt2HVmqo4HOHUrn5Mgm9C', 'admin');
