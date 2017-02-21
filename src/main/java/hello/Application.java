package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by jaok on 2016-11-03.
 */

@SpringBootApplication
@ComponentScan("controllers")
public class Application {

    public static void main(String [] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

//        System.out.println("Let's start!!'");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }
}
