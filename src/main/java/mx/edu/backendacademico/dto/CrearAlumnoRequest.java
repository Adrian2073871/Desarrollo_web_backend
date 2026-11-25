/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package mx.edu.backendacademico.dto;

/**
 *
 * @author Adrian Briseno
 */
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearAlumnoRequest(
        @NotBlank @Size(max = 30) String matricula,
@NotBlank @Size(min = 3, max = 120) String nombre,
@NotBlank @Email @Size(max = 160) String correo) {
}
