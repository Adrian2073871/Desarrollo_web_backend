/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.exception;

/**
 *
 * @author Adrian Briseno
 */
public class ClaveIdempotenciaReutilizadaException
        extends RuntimeException {

    public ClaveIdempotenciaReutilizadaException() {
        super(
            "La clave de idempotencia ya fue utilizada "
            + "con un contenido diferente"
        );
    }
}
