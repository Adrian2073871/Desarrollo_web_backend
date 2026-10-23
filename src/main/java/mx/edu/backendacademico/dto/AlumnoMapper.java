/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.dto;

/**
 *
 * @author Adrian Briseno
 */
// Convierte el valor de dominio a la representación pública sin acceder a la base.
import mx.edu.backendacademico.domain.Alumno;
public final class AlumnoMapper {
    private AlumnoMapper() {}
    public static AlumnoResponse toResponse(Alumno alumno) {
        // Sólo se publican los campos declarados por el DTO de salida.
        return new AlumnoResponse(alumno.id(), alumno.matricula(), alumno.nombre(), alumno.correo(), alumno.estatus());
    }
}
