/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package mx.edu.backendacademico.domain;

/**
 *
 * @author Adrian Briseno
 */
public record Materia(Long id, String clave, String nombre, int creditos) {
    public Materia {
        // Conserva las precondiciones incluso al construir desde una prueba.
        if (clave == null || clave.isBlank() || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Clave y nombre son obligatorios");
        }
        if (creditos <= 0) throw new IllegalArgumentException("Los créditos deben ser positivos");
    }
}
