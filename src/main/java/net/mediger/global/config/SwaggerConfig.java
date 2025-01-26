package net.mediger.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${server.url.local}")
    private String localServerUrl;

    @Value("${server.url.prod}")
    private String prodServerUrl;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(localServerUrl);
        server.description("Local Server");

        Server prodServer = new Server();
        prodServer.setUrl(prodServerUrl);
        prodServer.description("Production Server");

        Info info = new Info()
                .title("Mediger API")
                .version("v1.0.0")
                .description("Mediger Swagger API");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server,prodServer));
    }
}
