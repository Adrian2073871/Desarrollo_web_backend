/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.backendacademico.repository.jpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Adrian Briseno
 */
public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long> {
    boolean existsByAlumnoIdAndMateriaId(Long alumnoId, Long materiaId);
    long countByMateriaId(Long materiaId);

    // Carga la relación de cardinalidad muchos-a-uno requerida por cada DTO.
    @EntityGraph(attributePaths = "materia")
    Page<InscripcionEntity> findByAlumnoId(Long alumnoId, Pageable pageable);
}
