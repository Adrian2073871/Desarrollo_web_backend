/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.controller;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Adrian Briseno
 */
@RestController
@RequestMapping("/api/v1")
public class SaludoController {
    @GetMapping("/saludo")
    public Map<String, String> saludar(){
        return Map.of("mensaje", "Hola backend");
    }
}
