INSERT INTO `role`(role_id, role_name, role_desc, create_time)
VALUES(1, 'ROLE_ADMIN', '전체관리자', now());
INSERT INTO `role`(role_id, role_name, role_desc, create_time)
VALUES(2, 'ROLE_MANAGER', '내부관리자', now());
INSERT INTO `role`(role_id, role_name, role_desc, create_time)
VALUES(3, 'ROLE_USER', '일반사용자', now());