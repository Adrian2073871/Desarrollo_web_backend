/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * @author Adrian Briseno
 */
@SpringBootApplication
public class BackendAcademicoApplication {
    public static void main(String[] args) {
        // La JVM delega el ensamblado del contexto y del servidor a Spring.
        SpringApplication.run(BackendAcademicoApplication.class, args);
    }
    
}
