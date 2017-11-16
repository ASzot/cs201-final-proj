package com.cv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cv.coinigy.CoinigyRest;
import com.cv.cryptopia.CryptopiaRest;
import com.cv.util.Rest;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    // Reminder: you have to change the template to an actual context file.
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    // Inject to Rest controller
    //Rest.rest = (CoinigyRest)ctx.getBean("coinigyAPI");

    Rest.rest = new CryptopiaRest();

    SpringApplication.run(Application.class, args);
  }
}
