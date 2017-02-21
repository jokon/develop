package org.mobica.jokon;

import org.mobica.jokon.bean.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jaok on 2017-01-24.
 */

@RestController
@EnableAutoConfiguration
@SpringBootApplication
@PropertySource("classpath:values.properties")
@ImportResource("classpath:beans.xml")
public class Main {


    @Autowired
    private MyBean myBean;

    @Autowired
    @Qualifier(value = "second")
    private MyBean myBean2;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @RequestMapping("/")
    String home() {
        return myBean.getValue() + " " + myBean2.getValue();
    }

    public static final void  main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public MyBean getMyBean() {
        return myBean;
    }

    public void setMyBean(MyBean myBean) {
        this.myBean = myBean;
    }

    public MyBean getMyBean2() {
        return myBean2;
    }

    public void setMyBean2(MyBean myBean2) {
        this.myBean2 = myBean2;
    }
}
