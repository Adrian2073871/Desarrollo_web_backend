/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
/**
 *
 * @author Adrian Briseno
 */
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:contexto;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
    "spring.datasource.username=sa", "spring.datasource.password=",
    "spring.flyway.enabled=false", "spring.jpa.hibernate.ddl-auto=create-drop"
})
class BackendAcademicoApplicationTests {
    // Comprueba ensamblado y mapeo JPA. PostgreSQL y migraciones se prueban en U09.
    @Test void cargaElContextoConBaseEfimera() { }
}