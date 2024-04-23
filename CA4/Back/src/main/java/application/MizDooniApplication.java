package application;

import models.Restaurant;
import services.MizDooni;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "controllers")
public class MizDooniApplication {
    public static void main(String[] args) {
        MizDooni.getInstance().fetchAndStoreData("http://91.107.137.117:55/");
        SpringApplication.run(MizDooni.class, args);
    }
}