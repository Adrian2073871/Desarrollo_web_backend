/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.domain;

/**
 *
 * @author Adrian Briseno
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Adrian Briseno
 */
class AlumnoTest {
    @Test void bajaConservaIdentidadSinModificarElOriginal() {
        var alumno = new Alumno(1L, " a001 ", "Ada", "ada@u.mx", EstatusAlumno.ACTIVO);
        var baja = alumno.darDeBaja();
        // Comprueba tanto el resultado como la ausencia de efectos laterales.
        assertEquals("A001", baja.matricula());
        assertEquals(alumno.id(), baja.id());
        assertEquals(EstatusAlumno.BAJA, baja.estatus());
        assertEquals(EstatusAlumno.ACTIVO, alumno.estatus());
    }
<<<<<<< HEAD
 
=======
 /*  
>>>>>>> master
    @Test void reactivarConservaIdentidadSinModificarElOriginal(){
        var alumno = new Alumno(1L, " a001 ", "Ada", "ada@u.mx", EstatusAlumno.ACTIVO);
        var baja = alumno.darDeBaja();
        var reactivado = baja.reactivar();
        System.out.println("Alumno dado de baja: " + baja.nombre() + ", ESTATUS: " + baja.estatus());
        System.out.println("Reactivacion del alumno...\n");
        System.out.println("Alumno reactivado: " + reactivado.nombre() + ", ESTATUS: " + reactivado.estatus());
    }
    @Test void compararMismoIdYdiferenteCorreo(){
        var alumno1 = new Alumno(1L, " a001 ", "Ada", "ada@u.mx", EstatusAlumno.ACTIVO);
        var alumno2 = new Alumno(1L, " a001 ", "Ada", "ada.personal@u.mx", EstatusAlumno.ACTIVO);
        //Si es falso, la prueba falla
        assertEquals(alumno1, alumno2);
    }
<<<<<<< HEAD

=======
*/
>>>>>>> master
    @Test void rechazaMatriculaVacia() {
        assertThrows(IllegalArgumentException.class, () ->
            new Alumno(null, " ", "Ada", "ada@u.mx", EstatusAlumno.ACTIVO));
    }

    @Test void materiaRechazaCreditosNoPositivos() {
        assertThrows(IllegalArgumentException.class, () ->
                new Materia(null, "M1", "Análisis", 0));
    }
}