package de.freerider;

import de.freerider.model.Customer;
import de.freerider.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
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

    CustomerRepository repo = new CustomerRepository();

    Customer customer1 = new Customer("Urban", "Shirley", "surban@mail.de");
    Customer customer2 = new Customer("Hahn", "Nina", "akrasilnikov@mail.de");
    Customer customer3 = new Customer("Thomas", "Nicole", "nthomas@mail.de");
    Customer customer4 = new Customer("Müller", "Peter", "pmueller@mail.de");
    Customer customer5 = new Customer("Schmidt", "Laura", "lschmidt@gmail.de");

    repo.save(customer1);
    System.out.println("Sollte 1 sein: " + repo.count());

    System.out.println("____________________________________");

    List<Customer> customerList = new ArrayList<Customer>();
    customerList.add(customer2);
    customerList.add(customer3);
    customerList.add(customer4);
    customerList.add(customer5);

    repo.saveAll(customerList);
    System.out.println("Sollte 5 sein: " + repo.count());

    System.out.println("____________________________________");

    System.out.println(
      "Customer1: Shirley Urban -> \n" + repo.findById(customer1.getId())
    );

    System.out.println("____________________________________");

    System.out.println(
      "Sollte wahr sein (customer1 existiert): " +
      repo.existsById(customer1.getId())
    );

    System.out.println("____________________________________");

    System.out.println("Alle Customer: " + repo.findAll());

    System.out.println("____________________________________");

    List<String> ids = new ArrayList<String>();
    ids.add(customer5.getId());
    ids.add(customer4.getId());
    System.out.println(
      "Alle Customer aus ArrayList ids: " + repo.findAllById(ids)
    );

    System.out.println("____________________________________");
    System.out.println(
      customer1.getFirstName() +
      " " +
      customer1.getLastName() +
      " sollte gelöscht sein:"
    );

    repo.deleteById(customer1.getId());
    System.out.println(repo.findAll());

    System.out.println("____________________________________");

    System.out.println(
      customer2.getFirstName() +
      " " +
      customer2.getLastName() +
      " sollte gelöscht sein:"
    );

    repo.delete(customer2);
    System.out.println(repo.findAll());

    System.out.println("____________________________________");

    repo.deleteAllById(ids);
    System.out.println(
      "Nur noch Customer 3 ist übrig (" +
      customer3.getFirstName() +
      " " +
      customer3.getLastName() +
      "): " +
      repo.findAll()
    );

    System.out.println("____________________________________");
    repo.deleteAll();
    customerList.add(customer1);
    repo.saveAll(customerList);
    System.out.println(
      "Zuerst wurden alle wieder hinzugefügt, um danach gelöscht zu werden aus einem Iterable (ArrayList):"
    );
    System.out.println(repo.findAll());
    repo.deleteAll(customerList);
    System.out.println("Leeres Repo: " + repo.findAll());
  }
}
