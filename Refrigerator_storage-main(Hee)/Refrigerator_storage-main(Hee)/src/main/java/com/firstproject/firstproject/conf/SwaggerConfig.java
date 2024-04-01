package com.firstproject.firstproject.conf;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info( title = "MEMBER",
                description = "회원가입, 로그인, 로그아웃, 회원정보 수정, 회원삭제, 내정보 조회.",
                version = "v1.0.0"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        String[] paths = {"/member/**"}; //

        return GroupedOpenApi.builder()
                .group("memberController 만 보이게 됩니다.") // 추후 전체 컨트롤러로 변경
                .pathsToMatch(paths)
                .build();
    }

}