CREATE TABLE `account` (
    `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `password` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `created_date` datetime DEFAULT NULL,
    `modified_date` datetime DEFAULT NULL,
    PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `account_roles` (
    `account_id` bigint(20) NOT NULL,
    `role_name` varchar(255) NOT NULL,
    PRIMARY KEY (`account_id`,`role_name`),
    CONSTRAINT `fk_account_role` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `role` (
    `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) NOT NULL,
    `role_desc` varchar(255) DEFAULT NULL,
    `create_time` datetime NOT NULL,
    PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `resources` (
    `resource_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `resource_name` varchar(255) NOT NULL,
    `http_method` varchar(255) NULL,
    `order_num` int(11) NOT NULL,
    `resource_type` varchar(255) NOT NULL,
    `create_time` datetime NOT NULL,
    PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `role_resources` (
    `role_resource_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL,
    `resource_id` bigint(20) NOT NULL,
    PRIMARY KEY (`role_resource_id`),
    KEY `fk_role_resources_resource` (`resource_id`),
    KEY `fk_role_resources_role` (`role_id`),
    CONSTRAINT `fk_role_resources_resource` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`resource_id`),
    CONSTRAINT `fk_role_resources_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;