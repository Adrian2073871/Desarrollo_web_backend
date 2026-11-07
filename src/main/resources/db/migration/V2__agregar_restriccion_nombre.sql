/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Adrian Briseno
 * Created: 6 nov 2026
 */
ALTER TABLE alumnos
ADD CONSTRAINT ck_alumnos_nombre_no_vacio
CHECK (LENGTH(TRIM(nombre)) > 0);_

