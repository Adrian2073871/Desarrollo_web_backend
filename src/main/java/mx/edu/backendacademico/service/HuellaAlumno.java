/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.service;

/**
 *
 * @author Adrian Briseno
 */
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import mx.edu.backendacademico.domain.Alumno;

public final class HuellaAlumno {

    private HuellaAlumno() {
    }

    public static String calcular(Alumno alumno) {

        String contenido =
            representar(alumno.matricula()) +
            representar(alumno.nombre()) +
            representar(alumno.correo());

        try {
            MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                contenido.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(
                "No fue posible generar la huella", ex
            );
        }
    }

    private static String representar(String valor) {
        return valor.length() + ":" + valor;
    }
}