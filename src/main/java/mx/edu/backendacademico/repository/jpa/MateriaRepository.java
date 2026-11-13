/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.backendacademico.repository.jpa;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Adrian Briseno
 */
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from MateriaEntity m where m.id = :id")
    Optional<MateriaEntity> bloquearPorId(@Param("id") Long id);
    // La fila de materia funciona como punto común de coordinación en PostgreSQL.
}
