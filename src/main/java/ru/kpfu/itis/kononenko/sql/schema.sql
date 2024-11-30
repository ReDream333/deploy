drop table if exists usersPrim;
drop sequence if exists usersPrim_sequence;
drop table if exists public_views;
drop table if exists merge_requests;
drop table if exists parent_child_relations;
drop table if exists nodes;
drop table if exists trees;
drop table if exists users;
create sequence usersPrim_sequence
    start with      100
    increment by    1
    cache           5;

create table usersPrim(
                          id              bigint not null default nextval('usersPrim_sequence'),
                          login           varchar not null,
                          email           varchar not null,
                          password        varchar not null,
----------------------------------------------------------
                          constraint artist_id_pk primary key (id),
                          constraint artist_login_uq unique (login),
                          constraint artist_login_password_uq unique (login, password),
                          constraint artist_email_uq unique (email)
);

comment
    on table usersPrim is 'Таблица пользователей';
comment
    on column usersPrim.id is 'ИД пользователя';
comment
    on column usersPrim.login is 'Логин(имя) пользователя';
comment
    on column usersPrim.email is 'Емэйл пользователя';
comment
    on column usersPrim.password is 'Пароль пользователя в хэшированном виде';




-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR NOT NULL,
                       email VARCHAR UNIQUE NOT NULL,
                       password_hash VARCHAR NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

comment on table users is 'Таблица пользователя';
comment on column users.username is 'Тот же логин - имя пользователя';
comment on column users.password_hash is 'Пароль в хэшированном виде';
comment on column users.created_at is 'Дата и время регистрации';

-- Таблица деревьев
CREATE TABLE trees (
                       id SERIAL PRIMARY KEY,
                       user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                       name VARCHAR(255) NOT NULL,
                       is_private BOOLEAN DEFAULT TRUE,
                       merged_tree_id INT REFERENCES trees(id) ON DELETE SET NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
comment on table trees is 'Таблица деревьев';
comment on column trees.name is 'Название дерева';
comment on column trees.created_at is 'Дата и время создания дерева';

-- Таблица узлов дерева
CREATE TABLE nodes (
                       id SERIAL PRIMARY KEY,
                       tree_id INT NOT NULL REFERENCES trees(id) ON DELETE CASCADE,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) not null,
                       surname VARCHAR(255),
                       gender CHAR(1) CHECK (gender IN ('M', 'F')), -- M: Male, F: Female
                       birth_date DATE,
                       death_date DATE,
                       biography TEXT
);
comment on table nodes is 'Таблица узлов дерева';
comment on column nodes.first_name is 'Имя';
comment on column nodes.last_name is 'Фамилия';
comment on column nodes.surname is 'Отчество';
comment on column nodes.gender is 'Пол (М - мужчина, Ж - женщина)';
comment on column nodes.birth_date is 'Дата рождения';
comment on column nodes.death_date is 'Дата смерти (NULL, если человек жив)';
comment on column nodes.biography is 'Текстовая биография';


-- Таблица связей между узлами
CREATE TABLE parent_child_relations (
                               id SERIAL PRIMARY KEY,
                               parent_id INT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
                               child_id INT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
                               UNIQUE (parent_id, child_id)
);

comment on table parent_child_relations is 'Таблица связей родитель-ребенок';
comment on column parent_child_relations.parent_id is 'ID узла родителя';
comment on column parent_child_relations.child_id is 'ID узла ребенка';


--Таблица браков
CREATE TABLE marriages (
                           id SERIAL PRIMARY KEY,
                           spouse_male INT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
                           spouse_female INT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
                           start_date DATE NOT NULL,
                           end_date DATE
);

comment on table marriages is 'Таблица браков';
comment on column marriages.start_date is 'Дата регистрации брака';
comment on column marriages.end_date is 'Дата расторжения брака';



-- Таблица просмотров публичных деревьев
CREATE TABLE public_views (
                              id SERIAL PRIMARY KEY,
                              tree_id INT NOT NULL REFERENCES trees(id) ON DELETE CASCADE,
                              viewer_user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                              viewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

comment on table public_views is 'Таблица просмотров публичных деревьев';
comment on column public_views.viewer_user_id is ' ID пользователя, который просмотрел дерево';
comment on column public_views.viewed_at is 'Дата и время просмотра';


-- Таблица запросов на соединение деревьев
CREATE TABLE merge_requests (
                                id SERIAL PRIMARY KEY,
                                requester_tree_id INT NOT NULL REFERENCES trees(id) ON DELETE CASCADE,
                                target_tree_id INT NOT NULL REFERENCES trees(id) ON DELETE CASCADE,
                                common_ancestor_id INT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
                                status VARCHAR(50) DEFAULT 'pending', -- Статусы: pending, approved, rejected
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                UNIQUE (requester_tree_id, target_tree_id, common_ancestor_id)
);

comment on table merge_requests is 'Таблица запросов на соединение деревьев';
comment on column merge_requests.requester_tree_id is 'ID дерева, отправившего запрос';
comment on column merge_requests.target_tree_id is ' ID дерева, к которому направлен запрос';
comment on column merge_requests.common_ancestor_id  is 'ID узла общего предка';
comment on column merge_requests.status is 'Статус запроса (pending, approved, rejected)';
comment on column merge_requests.created_at is 'Дата создания merge_requests.';
