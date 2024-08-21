package com.oficinadobrito.api;

import com.oficinadobrito.api.Config.Filters.SecurityFilter;
import com.oficinadobrito.api.Config.SecurityConfig;
import com.oficinadobrito.api.Config.SwaggerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApiApplicationTests {

    @Autowired
    private  ApplicationContext apiContext;

    @DisplayName("Tested if the context is loaded successfully when the api is started")
    @Test
    void contextLoads() {
        //Given - Arrange / when  - Act
        //Then  - assert
        Assertions.assertNotNull(apiContext);
    }

    @DisplayName("When the api starts, the beans, Swagger, SecuritySecurity, FiltroSeguranca, are loaded")
    @Test
    void testBeanLoading() {
        //Given - Arrange / when  - Act
        //Then  - assert
        Assertions.assertNotNull(apiContext.getBean(SwaggerConfig.class), ()->"The swagger documentation configuration for the project failed");
        Assertions.assertNotNull(apiContext.getBean(SecurityConfig.class), ()->"There is some error in the spring security configuration");
        Assertions.assertNotNull(apiContext.getBean(SecurityFilter.class), ()->"The filter for jwt authentication system was not started");
    }
}
