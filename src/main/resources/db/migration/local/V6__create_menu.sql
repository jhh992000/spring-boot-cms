CREATE TABLE `menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `site_id` bigint(20) NOT NULL,
    `name` varchar(255) NOT NULL,
    `upper_menu_id` bigint(20) NOT NULL,
    `depth` bigint(20) NOT NULL,
    `sort_no` bigint(20) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `type` varchar(255) NOT NULL,
    `link_url` varchar(255) DEFAULT NULL,
    `open_type` varchar(255) NOT NULL,
    `hide` bit(1) NOT NULL,
    `enable` bit(1) NOT NULL,
    `created_datetime` datetime DEFAULT NULL,
    `modified_datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_menu_site` (`site_id`),
CONSTRAINT `fk_menu_site` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8