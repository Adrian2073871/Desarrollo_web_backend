/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package mx.edu.backendacademico.dto;

/**
 *
 * @author Adrian Briseno
 */
import mx.edu.backendacademico.domain.EstatusAlumno;
public record AlumnoResponse(Long id, String matricula, String nombre, String correo, EstatusAlumno estatus) {
    
}
