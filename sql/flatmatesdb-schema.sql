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

CREATE TABLE puntos_totales (
    loginid VARCHAR(15) NOT NULL,
    puntos INT NOT NULL,	
    FOREIGN KEY (loginid) REFERENCES users(loginid) on delete cascade,
    PRIMARY KEY (loginid, puntos)
);

CREATE TABLE puntos_grupo (
    loginid VARCHAR(16) NOT NULL,
    grupoid BINARY(16) NOT NULL,
    puntos INT NOT NULL,	
    FOREIGN KEY (loginid) REFERENCES users(loginid) on delete cascade,
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade,
    PRIMARY KEY (loginid, grupoid, puntos)
);


CREATE TABLE tareas (
    id BINARY (16) NOT NULL,
    grupoid BINARY (16) NOT NULL,
    userid BINARY (16) NOT NULL,	
    tarea VARCHAR (100) NOT NULL,
    image VARCHAR (500),
    punts int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade
  
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
	PRIMARY KEY (id),
	FOREIGN KEY (grupoid) REFERENCES grupo(id) on delete cascade
);

