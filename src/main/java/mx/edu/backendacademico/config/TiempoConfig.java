/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.config;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 *
 * @author Adrian Briseno
 */
@Configuration(proxyBeanMethods = false)
public class TiempoConfig {
    @Bean
    public Clock reloj() {
        // El tiempo es una dependencia sustituible por Clock.fixed en pruebas.
        return Clock.systemUTC();
    }
}
