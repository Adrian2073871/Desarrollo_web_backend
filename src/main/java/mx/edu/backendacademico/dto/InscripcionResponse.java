/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package mx.edu.backendacademico.dto;
import java.time.LocalDateTime;
/**
 *
 * @author Adrian Briseno
 */
public record InscripcionResponse(Long id, Long materiaId, String materia, LocalDateTime fecha) {

}
