
insert into account (account_id, username, password, created_date, modified_date)
values (1, 'jhh992000@gmail.com', '$2a$10$.j3Gpbi9x0mUzisVO.vk2OGhXERgIq4lg5PE2O9FCWL2Mh0fv7LLu', now(), null);

insert into account_roles (account_id, role_name)
values (1, 'USER');