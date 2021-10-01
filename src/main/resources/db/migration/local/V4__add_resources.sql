INSERT INTO resources(resource_id, resource_type, resource_name, http_method, order_num, create_time)
VALUES(1, 'url', '/api/admin/**', '', 0, now());

INSERT INTO resources(resource_id, resource_type, resource_name, http_method, order_num, create_time)
VALUES(2, 'url', '/api/account', '', 1, now());

INSERT INTO resources(resource_id, resource_type, resource_name, http_method, order_num, create_time)
VALUES(3, 'url', '/api/account/user-feature', '', 2, now());

INSERT INTO resources(resource_id, resource_type, resource_name, http_method, order_num, create_time)
VALUES(4, 'url', '/api/account/admin-feature', '', 3, now());
