package com.bulade.donor.framework.swagger.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置属性
 */
@ConfigurationProperties("donor.swagger")
@Data
public class SwaggerProperties {

    @NotEmpty(message = "标题不能为空")
    private String title;

    @NotEmpty(message = "描述不能为空")
    private String description;

    @NotEmpty(message = "作者不能为空")
    private String author;

    @NotEmpty(message = "版本不能为空")
    private String version;

}
