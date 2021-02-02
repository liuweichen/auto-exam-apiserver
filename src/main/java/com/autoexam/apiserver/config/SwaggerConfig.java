package com.autoexam.apiserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "server.swagger.enable", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {
  @Bean
  public Docket createRestApi() {
    // http://localhost:8080/swagger-ui.html 查看API文档
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.autoexam.apiserve"))
      .paths(PathSelectors.any())
      .build();
  }

  /**
   * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
   * @return
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Auto Exam 项目API")
      .description("Auto Exam 项目SwaggerAPI管理")
      .build();
  }
}

