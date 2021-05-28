package de.freerider.customermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

  @EventListener(ApplicationReadyEvent.class)
  public void doWhenApplicationReady() {
    System.out.println("Hello World!");
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
