package com.bulade.donor.framework.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Setter;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.JavadocProvider;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.SecurityService;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.models.servers.Server;

import java.util.*;

@Setter
@AutoConfiguration
@ConditionalOnClass({OpenAPI.class})
@EnableConfigurationProperties(SwaggerProperties.class)
@ConfigurationProperties(prefix = "springdoc")
public class SwaggerConfiguration {

    @Setter
    private List<String> serverUrls = new ArrayList<>();

    // ========== 全局 OpenAPI 配置 ==========
    @Bean
    public OpenAPI openAPI(SwaggerProperties properties) {
        var securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP) // 类型
            .name(HttpHeaders.AUTHORIZATION) // 请求头的 name
            .in(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT"); // token 所在位置

        var servers = serverUrls.stream().map((url) -> {
            var server = new Server();
            server.setUrl(url);
            return server;
        }).toList();
        return new OpenAPI()
            // 接口信息
            .info(buildInfo(properties))
            .servers(servers)
            // 接口安全配置
            .components(new Components().addSecuritySchemes("bearer-key", securityScheme))
            .security(List.of(new SecurityRequirement().addList("bearer-key")));
    }

    /**
     * API 摘要信息
     */
    private Info buildInfo(SwaggerProperties properties) {
        return new Info()
            .title(properties.getTitle())
            .description(properties.getDescription())
            .version(properties.getVersion());
    }

    /**
     * 自定义 OpenAPI 处理器
     */
    @Bean
    public OpenAPIService openApiBuilder(Optional<OpenAPI> openAPI,
                                         SecurityService securityParser,
                                         SpringDocConfigProperties springDocConfigProperties,
                                         PropertyResolverUtils propertyResolverUtils,
                                         Optional<List<OpenApiBuilderCustomizer>> openApiBuilderCustomizers,
                                         Optional<List<ServerBaseUrlCustomizer>> serverBaseUrlCustomizers,
                                         Optional<JavadocProvider> javadocProvider) {

        return new OpenAPIService(openAPI, securityParser, springDocConfigProperties,
            propertyResolverUtils, openApiBuilderCustomizers, serverBaseUrlCustomizers, javadocProvider);
    }

    // ========== 分组 OpenAPI 配置 ==========

    @Bean
    public GroupedOpenApi adminGroup() {
        String[] paths = {"/admin-api/**"};
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
                content.values().forEach(mediaType -> mediaType.schema(this.customizeSchema(mediaType.getSchema())));
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
