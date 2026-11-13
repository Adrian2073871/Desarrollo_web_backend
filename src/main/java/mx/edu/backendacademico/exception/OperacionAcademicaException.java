/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.exception;

/**
 *
 * @author Adrian Briseno
 */
public class OperacionAcademicaException extends RuntimeException {
    private final String codigo;

    public OperacionAcademicaException(String codigo, String mensaje) {
        super(mensaje);
        // El código distingue ausencia, duplicado, baja y falta de cupo.
        this.codigo = codigo;
    }

    public String getCodigo() { return codigo; }
}
