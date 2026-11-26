/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.repository;

/**
 *
 * @author Adrian Briseno
 */
import java.util.Optional;
import mx.edu.backendacademico.domain.Alumno;
import mx.edu.backendacademico.domain.EstatusAlumno;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdempotenciaAlumnoRepository {

    private final JdbcTemplate jdbc;

    public IdempotenciaAlumnoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean reservar(
            String usuario, String clave, String huella) {

        int filas = jdbc.update("""
            INSERT INTO idempotencia_alumnos
                (usuario, clave, huella)
            VALUES (?, ?, ?)
            ON CONFLICT (usuario, clave) DO NOTHING
            """,
            usuario, clave, huella
        );

        return filas == 1;
    }

    public Optional<Registro> buscar(
            String usuario, String clave) {

        return jdbc.query("""
            SELECT huella, alumno_id, matricula,
                   nombre, correo, estatus
            FROM idempotencia_alumnos
            WHERE usuario = ? AND clave = ?
            """,
            (rs, numeroFila) -> new Registro(
                rs.getString("huella"),
                new Alumno(
                    rs.getLong("alumno_id"),
                    rs.getString("matricula"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    EstatusAlumno.valueOf(
                        rs.getString("estatus")
                    )
                )
            ),
            usuario, clave
        ).stream().findFirst();
    }

    public void completar(
            String usuario, String clave, Alumno alumno) {

        int filas = jdbc.update("""
            UPDATE idempotencia_alumnos
            SET alumno_id = ?,
                matricula = ?,
                nombre = ?,
                correo = ?,
                estatus = ?
            WHERE usuario = ? AND clave = ?
            """,
            alumno.id(),
            alumno.matricula(),
            alumno.nombre(),
            alumno.correo(),
            alumno.estatus().name(),
            usuario,
            clave
        );

        if (filas != 1) {
            throw new IllegalStateException(
                "No fue posible completar la idempotencia"
            );
        }
    }

    public record Registro(
        String huella,
        Alumno alumno
    ) {
    }
}
