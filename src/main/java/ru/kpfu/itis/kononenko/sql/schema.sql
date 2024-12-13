
drop table if exists relationships;
drop sequence if exists users_sequence;
drop table if exists public_views;
drop table if exists merge_requests;
drop table if exists parent_child_relations;
drop table if exists marriages;
drop table if exists node_photos;
drop table if exists nodes;
drop table if exists trees;
drop table if exists users;



-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR NOT NULL,
                       email VARCHAR UNIQUE NOT NULL,
                       password_hash VARCHAR NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       photo VARCHAR
);

comment on table users is 'Таблица пользователя';
comment on column users.username is 'Тот же логин - имя пользователя';
comment on column users.password_hash is 'Пароль в хэшированном виде';
comment on column users.created_at is 'Дата и время регистрации';
comment on column users.photo is 'URL фото профиля';


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
                       biography TEXT,
                       photo VARCHAR
);
comment on table nodes is 'Таблица узлов дерева';
comment on column nodes.first_name is 'Имя';
comment on column nodes.last_name is 'Фамилия';
comment on column nodes.surname is 'Отчество';
comment on column nodes.gender is 'Пол (М - мужчина, Ж - женщина)';
comment on column nodes.birth_date is 'Дата рождения';
comment on column nodes.death_date is 'Дата смерти (NULL, если человек жив)';
comment on column nodes.biography is 'Текстовая биография';
comment on column nodes.photo is 'Главное фото узла';

-- Таблица фотоальбома узла
CREATE TABLE node_photos (
                       id SERIAL PRIMARY KEY,
                       node_id INT NOT NULL REFERENCES nodes(id) ON DELETE CASCADE,
                       photo_url TEXT,
                       description TEXT
);
comment on table node_photos is 'Таблица фотоальбома узла';
comment on column node_photos.node_id is 'ИД узла';
comment on column node_photos.photo_url is 'URL фотографии - ссылка на облако';
comment on column node_photos.description is 'Описание фотографий';

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
