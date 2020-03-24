package org.ril.hrss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*@ComponentScan({"com.ril.svc"})
@EntityScan("com.ril.svc.model")*/
@SpringBootApplication()
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}


