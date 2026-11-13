/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package mx.edu.backendacademico.dto;

/**
 *
 * @author Adrian Briseno
 */
import java.util.List;
import org.springframework.data.domain.Page;

public record PaginaResponse<T>(List<T> contenido, int pagina, int tamano,
                                long totalElementos, int totalPaginas) {
    public PaginaResponse {
        // Copia defensiva para que la lista de salida no cambie después.
        contenido = List.copyOf(contenido);
    }

    public static <T> PaginaResponse<T> de(Page<T> pagina) {
        return new PaginaResponse<>(pagina.getContent(), pagina.getNumber(),
            pagina.getSize(), pagina.getTotalElements(), pagina.getTotalPages());
    }
}