/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.service;

/**
 *
 * @author Adrian Briseno
 */
import mx.edu.backendacademico.domain.Alumno;
import mx.edu.backendacademico.domain.EstatusAlumno;
import mx.edu.backendacademico.exception.ClaveIdempotenciaReutilizadaException;
import mx.edu.backendacademico.exception.ClaveIdempotenciaInvalidaException;
import mx.edu.backendacademico.repository.IdempotenciaAlumnoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
public class IdempotenciaAlumnoService {

    private final AlumnoService alumnos;
    private final IdempotenciaAlumnoRepository repository;

    public IdempotenciaAlumnoService(
            AlumnoService alumnos,
            IdempotenciaAlumnoRepository repository) {

        this.alumnos = alumnos;
        this.repository = repository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Alumno crear(
            String usuario,
            String clave,
            String matricula,
            String nombre,
            String correo) {

        // 1. Validar la clave.
        if (clave == null || clave.isBlank()
                || clave.length() > 100) {

            throw new ClaveIdempotenciaInvalidaException();
        }

        // 2. Normalizar los datos.
        Alumno candidato = new Alumno(
            null,
            matricula,
            nombre,
            correo,
            EstatusAlumno.ACTIVO
        );

        // 3. Calcular la huella.
        String huella = HuellaAlumno.calcular(candidato);

        // 4. Intentar reservar la clave.
        boolean reservada = repository.reservar(
            usuario, clave, huella
        );

        // 5. Si la clave es nueva, crear el alumno.
        if (reservada) {

            Alumno creado = alumnos.crear(
                candidato.matricula(),
                candidato.nombre(),
                candidato.correo()
            );

            repository.completar(
                usuario, clave, creado
            );

            return creado;
        }

        // 6. Recuperar la operación anterior.
        var registro = repository.buscar(usuario, clave)
            .orElseThrow(() -> new IllegalStateException(
                "No se encontró la clave de idempotencia"
            ));

        // 7. Comparar el contenido.
        if (!registro.huella().equals(huella)) {
            throw new ClaveIdempotenciaReutilizadaException();
        }

        // 8. Devolver el resultado original.
        return registro.alumno();
    }
}
