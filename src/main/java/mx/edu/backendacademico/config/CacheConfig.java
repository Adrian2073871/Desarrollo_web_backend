/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.backendacademico.config;

/**
 *
 * @author Adrian Briseno
 */
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfig {
    @Bean
    CacheManager cacheManager() {
        // Difiere put/evict/clear hasta commit cuando hay una transacción activa.
        // Esta caché local es de laboratorio: no tiene TTL ni coordinación entre réplicas.
        return new TransactionAwareCacheManagerProxy(
            new ConcurrentMapCacheManager("alumnos", "alumnosLista"));
    }
}