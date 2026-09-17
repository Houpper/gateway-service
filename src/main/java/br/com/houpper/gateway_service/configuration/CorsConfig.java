package br.com.houpper.gateway_service.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Configuração de CORS (Cross-Origin Resource Sharing) para o gateway.
 * <br>
 * Esta classe define as regras de CORS que serão aplicadas a todas as rotas do gateway, permitindo que clientes de
 * origens específicas possam acessar os recursos do gateway.
 */
@Configuration
public class CorsConfig {

    /**
     * Lista de origens permitidas para CORS. As origens são lidas a partir da propriedade "cors.allowed-origins" no
     * arquivo de configuração da aplicação (application.yml).
     */
    @Value("${cors.allowed-origins}")
    private List<String> allowedOrigins;

    /**
     * Configura o filtro CORS para permitir requisições de origens específicas, métodos HTTP, cabeçalhos e credenciais.
     * As configurações são aplicadas a todas as rotas (/**).
     */
    @Bean
    CorsWebFilter corsWebFilter() {

        CorsConfiguration config = new CorsConfiguration();

        /*
         * Configura as origens permitidas para CORS. As origens são lidas a partir da propriedade
         * "cors.allowed-origins" no arquivo de configuração da aplicação (application.yml). Isso permite que apenas as
         * origens especificadas possam acessar os recursos do gateway.
         */
        config.setAllowedOrigins(allowedOrigins);

        /*
         * Configura os métodos HTTP permitidos para CORS. Neste caso, são permitidos os métodos GET, POST, PUT, DELETE,
         * PATCH, OPTIONS e HEAD. Isso garante que apenas esses métodos possam ser usados em requisições CORS para
         * acessar os recursos do gateway.
         */
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD"));

        /*
         * Configura os cabeçalhos permitidos para CORS. Neste caso, são permitidos os cabeçalhos "Authorization",
         * "Content-Type", "Accept", "Origin", "X-Tenant-ID" e "Cache-Control". Isso significa que os clientes CORS
         * poderão incluir esses cabeçalhos em suas requisições para acessar os recursos do gateway.
         */
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Tenant-ID",
                "Cache-Control"));

        /*
         * Configura os cabeçalhos expostos para CORS. Neste caso, o cabeçalho "X-Tenant-ID" é exposto, o que significa
         * que os clientes CORS poderão acessar esse cabeçalho na resposta.
         */
        config.setExposedHeaders(List.of("X-Tenant-ID"));

        /*
         * Configura se as credenciais (como cookies, cabeçalhos de autorização, etc.) são permitidas em requisições
         * CORS. Neste caso, as credenciais são permitidas, o que significa que os clientes CORS poderão enviar
         * credenciais com as requisições para acessar os recursos do gateway.
         */
        config.setAllowCredentials(true);

        /*
         * Configura o tempo máximo (em segundos) que as respostas CORS prévias podem ser armazenadas em cache pelos
         * navegadores. Neste caso, o tempo máximo é definido como 3600 segundos (1 hora), o que significa que os
         * navegadores poderão armazenar em cache as respostas CORS prévias por até 1 hora antes de fazer uma nova
         * solicitação prévia.
         */
        config.setMaxAge(3600L);

        /*
         * Configura a fonte de configuração CORS para o filtro. Neste caso, a configuração CORS é aplicada a todas as
         * rotas (/**) do gateway, o que significa que as regras de CORS definidas serão aplicadas a todas as
         * requisições que passarem pelo gateway.
         */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}