/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico;

/**
 *
 * @author Adrian Briseno
 */
import mx.edu.backendacademico.domain.Alumno;
import mx.edu.backendacademico.exception.MatriculaDuplicadaException;
import mx.edu.backendacademico.service.AlumnoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlumnoConcurrenciaTest {

    @Autowired
    private AlumnoService alumnoService;

    enum Resultado {
        EXITO,
        CONFLICTO
    }

    @Test
    void dosTareasIntentanCrearLaMismaMatricula()
            throws Exception {

        String matricula = "CONC-2026-001";

        CountDownLatch preparadas = new CountDownLatch(2);
        CountDownLatch inicio = new CountDownLatch(1);
        CountDownLatch finalizadas = new CountDownLatch(2);

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        try {
            Callable<Resultado> tarea = () -> {
                preparadas.countDown();

                try {
                    inicio.await();

                    try {
                        alumnoService.crear(
                                matricula,
                                "Alumno de prueba",
                                "prueba@universidad.mx"
                        );

                        return Resultado.EXITO;

                    } catch (MatriculaDuplicadaException e) {
                        return Resultado.CONFLICTO;
                    }

                } finally {
                    finalizadas.countDown();
                }
            };

            Future<Resultado> futuroA = executor.submit(tarea);
            Future<Resultado> futuroB = executor.submit(tarea);

            assertTrue(
                    preparadas.await(10, TimeUnit.SECONDS),
                    "Las dos tareas no llegaron a estar preparadas"
            );

            inicio.countDown();

            assertTrue(
                    finalizadas.await(10, TimeUnit.SECONDS),
                    "Las tareas no finalizaron dentro del límite"
            );

            List<Resultado> resultados = List.of(
                    futuroA.get(),
                    futuroB.get()
            );

            long exitos = resultados.stream()
                    .filter(r -> r == Resultado.EXITO)
                    .count();

            long conflictos = resultados.stream()
                    .filter(r -> r == Resultado.CONFLICTO)
                    .count();

            List<Alumno> encontrados = alumnoService.listar()
                    .stream()
                    .filter(a -> a.matricula().equals(matricula))
                    .toList();

            System.out.println("Resultados: " + resultados);
            System.out.println("Éxitos: " + exitos);
            System.out.println("Conflictos: " + conflictos);
            System.out.println("Registros encontrados: " + encontrados.size());
            System.out.println("Alumno guardado: " + encontrados);

            assertEquals(1, exitos);
            assertEquals(1, conflictos);
            assertEquals(1, encontrados.size());

        } finally {
            inicio.countDown();
            executor.shutdownNow();
        }
    }
}

