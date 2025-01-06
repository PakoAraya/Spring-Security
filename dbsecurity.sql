
-- Script para trabajar con proyecto Spring Security
CREATE TABLE usuario_login(
	id				serial PRIMARY KEY,
	name			varchar(50) UNIQUE NOT NULL,
	email 		varchar(100) UNIQUE NOT NULL,
	password 	varchar(255) NOT NULL,
	ROLE			varchar(20) NOT null
);

INSERT INTO usuario_login (name, email, password , role) VALUES
('Anita', 'anita@example.com', 'admin123', 'ADMIN'),
('David', 'david@example.com', 'admin123', 'ADMIN'),
('Sofia', 'sofia@example.com', 'user123', 'USER'),
('Steve', 'steve@example.com', 'user123', 'USER');


