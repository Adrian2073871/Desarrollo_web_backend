/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.example;

/**
 *
 * @author Adrian Briseno
 */
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
public class ConsultaSaludo {
    public static void main(String[] args) throws Exception {
        // Establece un límite para conectar y otro para completar esta solicitud.
        try (var cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2)).build()) {
            var peticion = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/saludo"))
                .timeout(Duration.ofSeconds(5)).GET().build();
            var respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            System.out.println(respuesta.statusCode());
            System.out.println(respuesta.headers().firstValue("Content-Type"));
            System.out.println(respuesta.body());
        }
    }
}

