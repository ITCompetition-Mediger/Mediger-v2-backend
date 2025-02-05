package net.mediger.global.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_SCHEME = "Bearer";
    private static final String JWT_FORMAT = "JWT";
    private static final String AUTH_HEADER = "Authorization";

    @Value("${server.url.prod}")
    private String prodServerUrl;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("/");
        server.description("Local Server");

        Server prodServer = new Server();
        prodServer.setUrl(prodServerUrl);
        prodServer.description("Production Server");

        Info info = new Info()
                .title("Mediger API")
                .version("v1.0.0")
                .description("Mediger Swagger API");

        Components components = getComponents();

        return new OpenAPI()
                .info(info)
                .servers(List.of(server, prodServer))
                .addSecurityItem(new SecurityRequirement().addList(AUTH_HEADER))
                .components(components);
    }

    private Components getComponents() {
        return new Components()
                .addSecuritySchemes(AUTH_HEADER,
                        new SecurityScheme()
                                .type(HTTP)
                                .scheme(BEARER_SCHEME)
                                .bearerFormat(JWT_FORMAT)
                                .in(HEADER)
                                .name(AUTH_HEADER)
                );
    }
}
