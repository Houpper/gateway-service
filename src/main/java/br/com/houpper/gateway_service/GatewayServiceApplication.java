package br.com.houpper.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Esta aplicação atua como um gateway de entrada, responsável por rotear as requisições para os serviços registrados no
 * ambiente (Eureka).
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApplication {

    /**
     * Inicializa a aplicação de gateway.
     *
     * @param args Os argumentos da linha de comando.
	 */
	static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
}