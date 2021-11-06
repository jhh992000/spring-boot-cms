INSERT INTO `role`(id, role_name, role_desc, list_order, created_datetime)
VALUES(1, 'ROLE_ADMIN', '전체관리자', 1, now());
INSERT INTO `role`(id, role_name, role_desc, list_order, created_datetime)
VALUES(2, 'ROLE_MANAGER', '내부관리자', 2, now());
INSERT INTO `role`(id, role_name, role_desc, list_order, created_datetime)
VALUES(3, 'ROLE_USER', '일반사용자', 3, now());