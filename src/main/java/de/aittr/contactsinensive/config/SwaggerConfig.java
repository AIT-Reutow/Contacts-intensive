package de.aittr.contactsinensive.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("LMS Service API").version("0.1"))
                .components(new Components()
                        .addSecuritySchemes("cookieAuth", securityScheme())
                        .addSchemas("EmailAndPassword", emailAndPasswordSchema()))
                .security(Collections.singletonList(new SecurityRequirement().addList("cookieAuth")))
                .paths(buildAuthenticationPath())
                .servers(Collections.singletonList(new Server().url("/")));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name("JSESSIONID")
                .in(SecurityScheme.In.COOKIE)
                .description("Авторизация через cookie с именем JSESSIONID.");
    }

    private Schema<?> emailAndPasswordSchema() {
        return new Schema<>()
                .type("object")
                .addProperties("username", new Schema<String>().type("string").example("user@example.com"))
                .addProperties("password", new Schema<String>().type("string").example("MyPass007!"))
                .required(Arrays.asList("username", "password"))
                .description("Данные для входа");
    }

    private Paths buildAuthenticationPath() {
        Paths paths = new Paths();

        PathItem loginPath = new PathItem()
                .post(new Operation()
                        .summary("Аутентификация")
                        .description("Маршрут для входа в систему")
                        .addTagsItem("Authentication")
                        .requestBody(new RequestBody()
                                .required(true)
                                .description("Данные для входа в формате form-data")
                                .content(new Content()
                                        .addMediaType("application/x-www-form-urlencoded",
                                                new MediaType()
                                                        .schema(formDataSchema()))))
                        .responses(new ApiResponses()
                                .addApiResponse("200", new ApiResponse().description("Успешная аутентификация"))
                                .addApiResponse("401", new ApiResponse().description("Неверные учетные данные"))));

        paths.addPathItem("/login", loginPath);

        return paths;
    }

    private Schema<?> formDataSchema() {
        Map<String, Schema> properties = new HashMap<>();

        // Добавляем поле username
        Schema<String> usernameSchema = new Schema<>();
        usernameSchema.setType("string");
        usernameSchema.setDescription("Email пользователя");
        usernameSchema.setExample("john.doe@example.com");
        properties.put("username", usernameSchema);

        // Добавляем поле password
        Schema<String> passwordSchema = new Schema<>();
        passwordSchema.setType("string");
        passwordSchema.setDescription("Пароль пользователя");
        passwordSchema.setExample("MyPass007!");
        properties.put("password", passwordSchema);

        // Создаем схему
        Schema<Object> schema = new Schema<>();
        schema.setType("object");
        schema.setProperties(properties);
        schema.setRequired(List.of("username", "password"));

        return schema;
    }


}
