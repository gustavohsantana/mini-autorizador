CREATE DATABASE IF NOT EXISTS miniautorizador;

CREATE USER 'user'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON miniautorizador.* TO 'user'@'%';

FLUSH PRIVILEGES;
