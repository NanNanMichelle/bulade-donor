package com.bulade.donor.framework.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Configuration
@ConfigurationProperties(prefix = "springdoc")
public class SwaggerConfiguration {

    @Setter
    private List<String> serverUrls = new ArrayList<>();

    @Bean
    public OpenAPI openAPI() {
        var securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
        var component = new Components()
            .addSecuritySchemes("bearer-key", securityScheme);
        var servers = serverUrls.stream().map((url) -> {
            var server = new Server();
            server.setUrl(url);
            return server;
        }).toList();

        return new OpenAPI()
            .servers(servers)
            .components(component)
            .security(List.of(new SecurityRequirement().addList("bearer-key")));
    }

    @Bean
    public GroupedOpenApi rdGroup() {
        String[] paths = {"/api/rd/**"};
        return this.setupGroupedOpenApi(paths, "研发", "研发", "研发端接口说明");
    }

    @Bean
    public GroupedOpenApi adminGroup() {
        String[] paths = {"/api/admin/**"};
        return this.setupGroupedOpenApi(paths, "管理端", "管理端", "管理端");
    }

    @Bean
    public GroupedOpenApi userV1Group() {
        String[] paths = {"/api/v1/**"};
        return this.setupGroupedOpenApi(paths, "用户端", "用户端", "用户端接口说明");
    }

    @Bean
    public GroupedOpenApi dictionariesGroup() {
        String[] paths = {"/api/dict/**"};
        return this.setupGroupedOpenApi(paths, "公共字典", "公共字典", "可公开调用的字典接口");
    }

    private GroupedOpenApi setupGroupedOpenApi(String[] paths, String group, String title, String description) {
        return GroupedOpenApi
            .builder()
            .pathsToMatch(paths)
            .group(group)
            .addOpenApiCustomizer(openApi -> {
                var info = new Info();
                info.setTitle(title);
                info.setDescription(description);

                openApi.setInfo(info);
            })
            .addOperationCustomizer((operation, handlerMethod) -> {
                var content = operation.getResponses().get("200").getContent();
                content.values().forEach(mediaType -> {
                    mediaType.schema(this.customizeSchema(mediaType.getSchema()));
                });
                return operation;
            })
            .build();
    }

    private Schema<?> customizeSchema(Schema<?> sourceSchema) {
        final Schema<?> wrapperSchema = new Schema<>();
        wrapperSchema.addProperty("success", new BooleanSchema()._default(true));
        wrapperSchema.addProperty("code", new IntegerSchema()._default(0));
        wrapperSchema.addProperty("message", new StringSchema()._default("string"));
        wrapperSchema.addProperty("data", sourceSchema);
        return wrapperSchema;
    }

}
