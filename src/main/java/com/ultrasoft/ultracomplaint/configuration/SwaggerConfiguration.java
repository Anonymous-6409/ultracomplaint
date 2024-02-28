package com.ultrasoft.ultracomplaint.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@OpenAPIDefinition
public class SwaggerConfiguration {

    @Bean
    OpenAPI custoOpenAPI(@Value("${spring.application.name}") String appDesciption,
                         @Value("${application.version}") String appVersion,
                         @Value("${application.title}") String title){
        return new OpenAPI()
                .info( new Info()
                        .description(appDesciption)
                        .title(title)
                        .version(appVersion).license(new License().name("Vartulz Technology Private Limited")
                                .url("https://vartulz.com")).contact(new Contact().email("gautam@vartulz.com")
                                .name("Goutam Kumar Sah")))
//                .addServersItem(new Server().description("DEV").url("http://localhost:7060"));
//              .addServersItem(new Server().description("DEV").url("https://6b21-122-160-63-37.ngrok-free.app"));
//                        .version(appVersion)
//                        .license(new License().name("Ultrasoftsys Private Limited").url("https://ultrasoftsys.com/"))
//                        .contact(new Contact().email("gautam@vartulz.com").name("Goutam Kumar Sah"))
                .addServersItem(new Server().description("DEV").url("http://127.0.0.1:7060"))
                .addServersItem(new Server().description("DEV").url("https://d10b-122-160-63-37.ngrok-free.app"))
                .addServersItem(new Server().description("UAT").url("https://complaints.ultrasoftsys.com"));
    }

    /**
     * ADMIN MASTER
     * @return
     */
    @Bean
    public GroupedOpenApi adminAPI() {
        return GroupedOpenApi.builder()
                .setGroup("ADMIN")
                .pathsToMatch("/admin/**")
                .build();
    }


    @Bean
    public GroupedOpenApi aseetAPI() {
        return GroupedOpenApi.builder()
                .setGroup("ASSET")
                .pathsToMatch("/asset/**")
                .build();
    }

    /**
     * CUSTOMER MASTER
     * @return
     */
    @Bean
    public GroupedOpenApi customerAPI() {
        return GroupedOpenApi.builder()
                .setGroup("CUSTOMER")
                .pathsToMatch("/customer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi LoginAPI() {
        return GroupedOpenApi.builder()
                .setGroup("LOGIN_LOGOUT")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi engineerAPI() {
        return GroupedOpenApi.builder()
                .setGroup("ENGINEER")
                .pathsToMatch("/engineer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi notificationAPI() {
        return GroupedOpenApi.builder()
                .setGroup("NOTIFICATION")
                .pathsToMatch("/push/notification/**")
                .build();
    }
}
