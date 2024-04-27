package application;

import config.AppConfig;
import models.Restaurant;
import org.springframework.context.annotation.Import;
import services.MizDooni;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@Import(AppConfig.class)
@ComponentScan(basePackages = "controllers")
public class MizDooniApplication {
    public static void main(String[] args) {
        MizDooni.getInstance();
        SpringApplication.run(MizDooniApplication.class, args);
    }
}