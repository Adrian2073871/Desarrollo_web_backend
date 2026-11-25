/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.config;

/**
 *
 * @author Adrian Briseno
 */
import java.net.URI;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CalendarioConfig.Propiedades.class)
public class CalendarioConfig {
    @ConfigurationProperties("calendario")
    public record Propiedades(URI baseUrl) { }

    @Bean
    RestClient calendarioRestClient(Propiedades propiedades) {
        var transporte = new SimpleClientHttpRequestFactory();
        // Límites distintos: establecer conexión y esperar datos de lectura.
        transporte.setConnectTimeout(Duration.ofSeconds(2));
        transporte.setReadTimeout(Duration.ofSeconds(3));
        return RestClient.builder().baseUrl(propiedades.baseUrl().toString())
            .requestFactory(transporte).build();
    }
}