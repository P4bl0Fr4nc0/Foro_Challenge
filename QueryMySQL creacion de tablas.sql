
create database api2;

use api2;

CREATE TABLE topico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    curso VARCHAR(255) NOT NULL,
    anio INT
);

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(255) NOT NULL,
    contraseña VARCHAR(300) NOT NULL
);
