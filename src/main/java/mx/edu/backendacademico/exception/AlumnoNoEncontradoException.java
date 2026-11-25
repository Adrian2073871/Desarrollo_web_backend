/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.exception;

/**
 *
 * @author Adrian Briseno
 */
public class AlumnoNoEncontradoException extends RuntimeException {
    public AlumnoNoEncontradoException(Long id) {
        super("No existe el alumno con id " + id);
    }
}
