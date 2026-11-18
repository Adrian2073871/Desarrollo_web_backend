/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.service;

/**
 *
 * @author Adrian Briseno
 */
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import mx.edu.backendacademico.exception.MatriculaDuplicadaException;
import mx.edu.backendacademico.exception.AlumnoNoEncontradoException;
import mx.edu.backendacademico.repository.AlumnoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AlumnoServiceTest {
    @Test void unDuplicadoNoDebeIntentarGuardarse() {
        var repository = mock(AlumnoRepository.class);
        when(repository.existePorMatricula("A1")).thenReturn(true);
        var servicio = new AlumnoService(repository);
        // Verifica la regla y su efecto: la operación inválida no escribe.
        assertThrows(MatriculaDuplicadaException.class, () -> servicio.crear("A1", "Ada", "ada@u.mx"));
        verify(repository, never()).guardar(any());
    }

    @Test void unaAusenciaTieneSignificadoDeDominio() {
        var repository = mock(AlumnoRepository.class);
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());
        assertThrows(AlumnoNoEncontradoException.class, () -> new AlumnoService(repository).buscar(99L));
    }
}
