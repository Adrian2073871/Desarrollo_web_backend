/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.backendacademico.repository.jpa;

/**
 *
 * @author Adrian Briseno
 */
// Spring Data construye una implementación a partir de la interfaz y sus consultas.
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AlumnoSpringDataRepository extends JpaRepository<AlumnoEntity, Long> {
    boolean existsByMatriculaIgnoreCase(String matricula);
    Optional<AlumnoEntity> findByMatriculaIgnoreCase(String matricula);
}
