/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Adrian Briseno
 * Created: 24 nov 2026
 */
CREATE TABLE idempotencia_alumnos (
    usuario VARCHAR(100) NOT NULL,
    clave VARCHAR(100) NOT NULL,
    huella CHAR(64) NOT NULL,

    alumno_id BIGINT,
    matricula VARCHAR(30),
    nombre VARCHAR(120),
    correo VARCHAR(160),
    estatus VARCHAR(20),

    creado_en TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_idempotencia_alumnos
        PRIMARY KEY (usuario, clave)
);
