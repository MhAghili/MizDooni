package application;

import config.AppConfig;
import models.Restaurant;
import org.springframework.context.annotation.Import;
import services.MizDooni;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@Import(AppConfig.class)
public class MizDooniApplication {
    public static void main(String[] args) {
        MizDooni.fetchAndStoreData("http://91.107.137.117:55/");
        SpringApplication.run(MizDooni.class, args);
    }
}