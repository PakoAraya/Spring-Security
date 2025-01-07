
-- Script to work with Spring Security learning project
CREATE TABLE user_login(
	id				serial PRIMARY KEY,
	name			varchar(100) UNIQUE NOT NULL,
	email 		varchar(100) UNIQUE NOT NULL,
	password 	varchar(50) NOT NULL,
	ROLE			varchar(50) NOT null
);

INSERT INTO user_login (name, email, password , role) VALUES
('Anita', 'anita@example.com', 'admin123', 'ADMIN'),
('David', 'david@example.com', 'admin123', 'ADMIN'),
('Sofia', 'sofia@example.com', 'user123', 'USER'),
('Steve', 'steve@example.com', 'user123', 'USER');


