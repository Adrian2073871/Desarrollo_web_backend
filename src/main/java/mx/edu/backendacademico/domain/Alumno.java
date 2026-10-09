/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package mx.edu.backendacademico.domain;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Adrian Briseno
 */
public record Alumno(Long id, String matricula, String nombre, String correo, EstatusAlumno estatus) {
     public Alumno {
        // 1. La identidad todavía puede ser null antes de persistir.
        if (id != null && id <= 0) throw new IllegalArgumentException("Id inválido");
        // 2. La misma matrícula debe tener una representación canónica.
        matricula = texto(matricula, 30, "matrícula").toUpperCase(Locale.ROOT);
        nombre = texto(nombre, 120, "nombre");
        correo = texto(correo, 160, "correo");
        Objects.requireNonNull(estatus, "El estatus es obligatorio");
    }

    private static String texto(String valor, int maximo, String campo) {
        if (valor == null || valor.isBlank() || valor.strip().length() > maximo) {
            throw new IllegalArgumentException("Valor inválido para " + campo);
        }
        return valor.strip();
    }

    public Alumno darDeBaja() {
        // 3. Se obtiene otro valor; las referencias al valor anterior no cambian.
        return new Alumno(id, matricula, nombre, correo, EstatusAlumno.BAJA);
    }
    /*
    public Alumno reactivar(){
        return new Alumno(id, matricula, nombre, correo, EstatusAlumno.ACTIVO);
    }

    @Override
    public boolean equals(Object obj){
        // Verificación rápida: mismo objeto
        if (this == obj) return true;

        // Verificación de null y tipo
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Alumno other = (Alumno) obj;
        return Objects.equals(id, other.id);
    }
    
    // Sobrescribir hashCode
    @Override
    public int hashCode() {
        // Usar Objects.hash para generar un hash consistente
        return Objects.hash(id);
    }
*/
}
