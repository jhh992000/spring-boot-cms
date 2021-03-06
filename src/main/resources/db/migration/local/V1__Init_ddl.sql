CREATE TABLE `account` (
    `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `password` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `account_roles` (
    `account_id` bigint(20) NOT NULL,
    `role_name` varchar(255) NOT NULL,
    PRIMARY KEY (`account_id`,`role_name`),
    CONSTRAINT `fk_account_role` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) NOT NULL,
    `role_desc` varchar(255) DEFAULT NULL,
    `list_order` bigint(20) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `resources` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `resource_name` varchar(255) NOT NULL,
    `http_method` varchar(255) NULL,
    `order_num` int(11) NOT NULL,
    `resource_type` varchar(255) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_resources` (
    `role_resource_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL,
    `resource_id` bigint(20) NOT NULL,
    PRIMARY KEY (`role_resource_id`),
    KEY `fk_role_resources_resource` (`resource_id`),
    KEY `fk_role_resources_role` (`role_id`),
    CONSTRAINT `fk_role_resources_resource` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`),
    CONSTRAINT `fk_role_resources_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `site` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `enabled` bit(1) NOT NULL,
    `alias` varchar(255) NOT NULL,
    `use_login_lock` bit(1) NOT NULL,
    `count_of_login_fail` int(11) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_site_alias` (`alias`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `site_id` bigint(20) NOT NULL,
    `parent_id` bigint(20),
    `list_order` bigint(20) NOT NULL,
    `name` varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `open_type` varchar(255) NOT NULL,
    `hide` bit(1) NOT NULL,
    `enable` bit(1) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_menu_site` (`site_id`),
    CONSTRAINT `fk_menu_site` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`),
    CONSTRAINT `fk_menu` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL,
    `site_id` bigint(20) NOT NULL,
    `menu_id` bigint(20) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_role_menu_menu` (`menu_id`),
    KEY `fk_role_menu_role` (`role_id`),
    KEY `fk_role_menu_site` (`site_id`),
    CONSTRAINT `fk_role_menu_menu` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
    CONSTRAINT `fk_role_menu_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT `fk_role_menu_site` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;