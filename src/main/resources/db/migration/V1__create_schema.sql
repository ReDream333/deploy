-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR UNIQUE NOT NULL,
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
                       gender CHAR(1) CHECK (gender IN ('M', 'F')), -- M: Male, F: Female
                       birth_date DATE,
                       death_date DATE,
                       comment TEXT,
                       photo VARCHAR
);
comment on table nodes is 'Таблица узлов дерева';
comment on column nodes.first_name is 'Имя';
comment on column nodes.last_name is 'Фамилия';
comment on column nodes.gender is 'Пол (М - мужчина, Ж - женщина)';
comment on column nodes.birth_date is 'Дата рождения';
comment on column nodes.death_date is 'Дата смерти (NULL, если человек жив)';
comment on column nodes.comment is 'Текстовый комментарий';
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


-- Таблица биографий
CREATE TABLE nodes_biography (
                                 id SERIAL PRIMARY KEY,
                                 node_id INT NOT NULL UNIQUE REFERENCES nodes(id) ON DELETE CASCADE,
                                 biography TEXT
);

comment on table nodes_biography is 'Таблица биографий узлов';
comment on column nodes_biography.node_id is 'ID узла';
comment on column nodes_biography.biography is 'Биография';
