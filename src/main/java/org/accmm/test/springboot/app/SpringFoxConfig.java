package org.accmm.test.springboot.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    /**
     * configuracion de swagger que automatiza la generacion de documentacion de los controladores en este caso, se configura el package que se quiere realizar
     * y el path selector a los cuales se realizaran las pruebas
     * @return docket
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.accmm.test.springboot.app.controllers"))
                .paths(PathSelectors.ant("/api/cuentas/**"))
                .build();
    }
}
