package Coupon_Project_Spring;


import Coupon_Project_Spring.Services.ClientService;
import Coupon_Project_Spring.Test.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;

/**
 * Main class, running the application.
 * @SpringBootApplication - indicates that this is a Spring Boot application.
 * @EnableScheduling - enables the scheduling of the daily job.
 */

@SpringBootApplication
//@EnableScheduling
public class Application{

    public static void main(String[] args) throws Exception {
        
        ApplicationContext context = SpringApplication.run(Application.class, args);
        Test test = context.getBean(Test.class);
        test.testAll();
    }

    // @Bean annotation tells Spring that a method annotated with @Bean
    // will return an object that should be registered as a bean in the Spring application context.
    // by default, the bean is a singleton.
    // bean needs to be in a configuration class, or in a class annotated with @Component.
    
    @Bean 
    public HashMap<String, ClientService> tokenStore(){
        return new HashMap<>();
    }
    
}
