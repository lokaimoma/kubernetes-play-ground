CREATE USER user WITH PASSWORD password
CREATE DATABASE IF NOT EXISTS bookloan;
CREATE DATABASE IF NOT EXISTS keycloakdb;
GRANT ALL PRIVILEGES ON DATABASE bookloan TO user;
GRANT ALL PRIVILEGES ON DATABASE keycloakdb TO user;
