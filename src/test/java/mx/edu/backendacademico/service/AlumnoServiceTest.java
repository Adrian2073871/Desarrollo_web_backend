/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.service;

/**
 *
 * @author Adrian Briseno
 */
import java.util.Optional;
import mx.edu.backendacademico.domain.Alumno;
import mx.edu.backendacademico.domain.EstatusAlumno;
import mx.edu.backendacademico.repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import mx.edu.backendacademico.exception.MatriculaDuplicadaException;
import mx.edu.backendacademico.exception.AlumnoNoEncontradoException;
import mx.edu.backendacademico.repository.AlumnoRepository;
@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {
    @Mock
    private AlumnoRepository repository;

    @InjectMocks
    private AlumnoService service;

    @Test void propietarioPuedeConsultarSuExpediente() {
        Alumno alumno = new Alumno(
            1L, "S1", "Ada", "ada@u.mx", EstatusAlumno.ACTIVO
        );

        when(repository.buscarPorMatricula("S1"))
            .thenReturn(Optional.of(alumno));

        Alumno resultado = service.buscarExpediente("S1", "S1", false);

        assertEquals("S1", resultado.matricula());
    }

    @Test void alumnoNoPuedeConsultarExpedienteAjeno() {
        assertThrows(
            AccessDeniedException.class,
            () -> service.buscarExpediente("S2", "S1", false)
        );

        // Comprueba que ni siquiera se intenta buscar el expediente ajeno.
        verify(repository, never()).buscarPorMatricula("S2");
    }

    @Test void administradorPuedeConsultarExpedienteAjeno() {
        Alumno alumno = new Alumno(
            2L, "S2", "Grace", "grace@u.mx", EstatusAlumno.ACTIVO
        );

        when(repository.buscarPorMatricula("S2"))
            .thenReturn(Optional.of(alumno));

        Alumno resultado = service.buscarExpediente("S2", "admin", true);

        assertEquals("S2", resultado.matricula());
    }
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
