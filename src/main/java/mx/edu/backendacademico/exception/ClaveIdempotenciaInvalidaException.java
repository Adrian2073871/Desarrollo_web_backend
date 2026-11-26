/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.exception;

/**
 *
 * @author Adrian Briseno
 */
public class ClaveIdempotenciaInvalidaException
        extends RuntimeException {

    public ClaveIdempotenciaInvalidaException() {
        super(
            "La clave de idempotencia debe contener "
            + "entre 1 y 100 caracteres y no estar vacía"
        );
    }
}
