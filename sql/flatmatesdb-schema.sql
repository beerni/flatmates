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
    info VARCHAR(255),
    tareas int NOT NULL,
    imagen VARCHAR (36),
    puntos int NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE grupo (
    id BINARY(16) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    info VARCHAR(500),
    admin BINARY(16) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (admin) REFERENCES users(id) on delete cascade      
);

CREATE TABLE grupousuario (
    userid BINARY(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    puntos INT NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade,
    PRIMARY KEY (userid, grupoid)
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

CREATE TABLE tareas (
    id BINARY (16) NOT NULL,
    grupoid BINARY (16) NOT NULL,
    userid BINARY (16),	
    tarea VARCHAR (100) NOT NULL,
    imagen VARCHAR (36),
    punts int NOT NULL,
    hecho enum ('false','true'),
    PRIMARY KEY (id),
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
  
);

CREATE TABLE relacionPuntosTareas (
    userid BINARY(16) NOT NULL,
    idtarea BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (idtarea) REFERENCES tareas(id) on delete cascade,
    PRIMARY KEY (userid, idtarea)
);
CREATE TABLE mensaje (
    id BINARY(16) NOT NULL,
    userid BINARY(16) NOT NULL,
    subject VARCHAR(100) NOT NULL,	
    mensaje VARCHAR(500) NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
  
);

CREATE TABLE listacompra (
	id BINARY (16) NOT NULL,
	item VARCHAR (100) NOT NULL,
	grupoid BINARY (16) NOT NULL,
    hecho TINYINT(1),
	PRIMARY KEY (id),
	FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade
);
