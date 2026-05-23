package umc.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swagger() {
        Info info = new Info().title("UMC10th API").description("UMC 10기 미션 API 명세서").version("0.0.1");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}