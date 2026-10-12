/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.edu.backendacademico.repository;
import java.util.List;
import java.util.Optional;
import mx.edu.backendacademico.domain.Alumno;

/**
 *
 * @author Adrian Briseno
 */
public interface AlumnoRepository {
    Alumno guardar(Alumno alumno);
    List<Alumno> buscarTodos();
    Optional<Alumno> buscarPorId(Long id);
    boolean existePorMatricula(String matricula);
    void eliminarPorId(Long id);
}
