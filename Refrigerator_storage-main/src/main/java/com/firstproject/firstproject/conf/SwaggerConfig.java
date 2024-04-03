package com.firstproject.firstproject.conf;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info( title = "냉장고 게시판 ",
                description = "냉장고 게시판 및 레시피 ",
                version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        String[] paths = {"/**"}; //

        return GroupedOpenApi.builder()
                .group("모든 Controller") // 추후 전체 컨트롤러로 변경
                .pathsToMatch(paths)
                .build();
    }

}