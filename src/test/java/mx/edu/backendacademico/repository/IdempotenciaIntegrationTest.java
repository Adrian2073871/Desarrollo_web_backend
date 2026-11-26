/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.repository;

/**
 *
 * @author Adrian Briseno
 */
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import mx.edu.backendacademico.repository.jpa.AlumnoSpringDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("jpa")
@Testcontainers
class IdempotenciaIntegrationTest {

    @Container
    static PostgreSQLContainer postgres =
        new PostgreSQLContainer("postgres:17-alpine");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    private MvcResult solicitarCreacion(
        String clave, String contenido) throws Exception {

        return mvc.perform(
            post("/api/v1/alumnos")
                .with(httpBasic("docente", "docente123"))
                .with(csrf())
                .header("Idempotency-Key", clave)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contenido)
        ).andReturn();
    }

    @Autowired
    MockMvc mvc;

    @Autowired
    AlumnoSpringDataRepository alumnos;
    
    @Test void dosSolicitudesSimultaneasCreanUnSoloAlumno()
            throws Exception {

        String clave = UUID.randomUUID().toString();

        String matricula = "IDEM-" +
            UUID.randomUUID().toString().substring(0, 8);

        String contenido = """
            {
                "matricula": "%s",
                "nombre": "Ada Lovelace",
                "correo": "ada@u.mx"
            }
            """.formatted(matricula);

        long cantidadInicial = alumnos.count();

        CountDownLatch preparados = new CountDownLatch(2);
        CountDownLatch inicio = new CountDownLatch(1);

        try (var executor = Executors.newFixedThreadPool(2)) {

            var primera = executor.submit(() -> {
                preparados.countDown();
                if (!inicio.await(10, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Tiempo agotado");
                }
                return solicitarCreacion(clave, contenido);
            });

            var segunda = executor.submit(() -> {
                preparados.countDown();
                if (!inicio.await(10, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Tiempo agotado");
                }
                return solicitarCreacion(clave, contenido);
            });

            assertTrue(preparados.await(10, TimeUnit.SECONDS));

            inicio.countDown();

            var respuesta1 = primera.get(30, TimeUnit.SECONDS);
            var respuesta2 = segunda.get(30, TimeUnit.SECONDS);

            assertAll(
                () -> assertEquals(
                    201, respuesta1.getResponse().getStatus()),

                () -> assertEquals(
                    201, respuesta2.getResponse().getStatus()),

                () -> assertEquals(
                    respuesta1.getResponse().getContentAsString(),
                    respuesta2.getResponse().getContentAsString()),

                () -> assertNotNull(
                    respuesta1.getResponse().getHeader("Location")),

                () -> assertEquals(
                    respuesta1.getResponse().getHeader("Location"),
                    respuesta2.getResponse().getHeader("Location")),

                () -> assertEquals(
                    cantidadInicial + 1, alumnos.count())
            );
        }
    }
    
    @Test void mismaClaveConDistintoContenidoDevuelveConflicto()
            throws Exception {

        String clave = UUID.randomUUID().toString();

        String matricula1 = "IDEM-" +
            UUID.randomUUID().toString().substring(0, 8);

        String matricula2 = "IDEM-" +
            UUID.randomUUID().toString().substring(0, 8);

        String contenidoOriginal = """
            {
                "matricula": "%s",
                "nombre": "Ada Lovelace",
                "correo": "ada@u.mx"
            }
            """.formatted(matricula1);

        String contenidoDiferente = """
            {
                "matricula": "%s",
                "nombre": "Emmy Noether",
                "correo": "emmy@u.mx"
            }
            """.formatted(matricula2);

        long cantidadInicial = alumnos.count();

        var primera = solicitarCreacion(
            clave, contenidoOriginal);

        assertEquals(201, primera.getResponse().getStatus());

        mvc.perform(
            post("/api/v1/alumnos")
                .with(httpBasic("docente", "docente123"))
                .with(csrf())
                .header("Idempotency-Key", clave)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contenidoDiferente)
        )
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.codigo")
                .value("CLAVE_IDEMPOTENCIA_REUTILIZADA"));

        assertEquals(cantidadInicial + 1, alumnos.count());
    }

}
