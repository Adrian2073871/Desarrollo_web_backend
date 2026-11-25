/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.service;

/**
 *
 * @author Adrian Briseno
 */
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CalendarioClient {
    private final RestClient client;

    public CalendarioClient(RestClient calendarioRestClient) {
        this.client = calendarioRestClient;
    }

    public Periodo consultarPeriodo(String clave) {
        // 1. Expande el segmento como parámetro, sin concatenar una URL del usuario.
        var periodo = client.get().uri("/periodos/{clave}", clave)
            // 2. retrieve propaga 4xx/5xx como excepciones específicas de HTTP.
            .retrieve().body(Periodo.class);
        if (periodo == null) throw new IllegalStateException("El calendario respondió sin cuerpo");
        return periodo;
    }

    public record Periodo(String clave, LocalDate inicio, LocalDate fin) { }
}
