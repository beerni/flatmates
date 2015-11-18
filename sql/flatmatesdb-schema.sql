drop database if exists flatmatesdb;
create database flatmatesdb;

use flatmatesdb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    sexo ENUM ('hombre','mujer'),
    informacio VARCHAR(500),
    puntos INT,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered','admin'),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
);
CREATE TABLE grupo (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    info VARCHAR(500),
    admin VARCHAR(16) NOT NULL,
    puntos INT NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade    
);

CREATE TABLE tareas (
    id BINARY(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,	
    tarea VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
  
);
CREATE TABLE mensajegrupo (
    id BINARY(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,	
    mensaje VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
  
);
CREATE TABLE mensaje (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,	
    mensaje VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
);