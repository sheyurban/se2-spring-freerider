package de.freerider.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.freerider.model.Customer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepositoryTest {

  @Autowired
  CrudRepository<Customer, String> customerRepository;

  //sample customers
  private Customer mats;
  private Customer thomas;

  @BeforeEach
  public void CustomersSetup() {
    mats = new Customer("Matsen", "Mats", "mmatsen@email.de");
    thomas = new Customer("Thomasen", "Thomas", "tthomasen@email.de");
  }

  @AfterEach
  public void ClearRepository() {
    customerRepository.deleteAll();
  }

  // Konstruktor-Tests:
  @Test
  void testThatRepoIsEmpty() {
    long customerCount = customerRepository.count();
    assertEquals(customerCount, 0);
    assertNotNull(customerRepository);
  }

  // save()-Tests:
  @Test
  void testSaveCustomers() {
    customerRepository.save(mats);
    long customerCount = customerRepository.count();
    assertEquals(customerCount, 1);

    customerRepository.save(thomas);
    customerCount = customerRepository.count();
    assertEquals(customerCount, 2);
  }

  @Test
  void testCustomerGetsId() {
    customerRepository.save(mats);
    String idMats = mats.getId();
    assertNotNull(idMats);
  }

  @Test
  void testSaveIdNotNull() {
    mats.setId("123");
    customerRepository.save(mats);
    String idMats = mats.getId();
    assertEquals(idMats, "123");
    Boolean idInRepo = customerRepository.existsById("123");
    assertTrue(idInRepo);
  }

  @Test
  void testSaveNullValue() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.save(null);
      }
    );
  }

  @Test
  void testSaveOnlyOnce() {
    customerRepository.save(mats);
    long customerCount = customerRepository.count();
    assertEquals(customerCount, 1);
    customerRepository.save(mats);
    customerCount = customerRepository.count();
    assertEquals(customerCount, 1);
  }

  @Test
  void testSaveUniqueId() {
    mats.setId("123");
    thomas.setId("123");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    assertEquals(customerRepository.count(), 1);
    Customer customer123 = customerRepository.findById("123").get();
    String customer123Vorname = customer123.getFirstName();
    assertNotEquals(customer123Vorname, "Mats");
    assertEquals(customer123Vorname, "Thomas");
  }

  // saveAll()-Tests:
  @Test
  void testSaveSome() {
    List<Customer> customerList = new ArrayList<Customer>();
    customerList.add(mats);
    customerList.add(thomas);
    customerRepository.saveAll(customerList);
    long customerCount = customerRepository.count();
    assertEquals(customerCount, 2);
  }

  @Test
  void testSaveAllNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.saveAll(null);
      }
    );
  }

  @Test
  void testSaveAllEmptyIterable() {
    List<Customer> customerList = new ArrayList<Customer>();
    long before = customerRepository.count();
    customerRepository.saveAll(customerList);
    long after = customerRepository.count();
    assertEquals(after, before);
  }

  // findById()-Tests:
  @Test
  void testFindById() {
    mats.setId("123");
    customerRepository.save(mats);
    Customer found = customerRepository.findById("123").get();
    assertEquals(found, mats);
  }

  @Test
  void testFindByIdNotFound() {
    Optional<Customer> found = customerRepository.findById("notfound");
    assertEquals(found, Optional.empty());
  }

  @Test
  void testFindByIdNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.findById(null);
      }
    );
  }

  @Test
  void testFindByIdEmpty() {
    assertEquals(customerRepository.findById(""), Optional.empty());
  }

  // findAll()-Tests:
  @Test
  void testFindAll() {
    List<Customer> customerList = new ArrayList<Customer>();
    customerList.add(mats);
    customerList.add(thomas);
    customerRepository.saveAll(customerList);
    Collection<Customer> foundCustomerList = (Collection<Customer>) customerRepository.findAll();
    assertTrue(foundCustomerList.containsAll(customerList));
  }

  @Test
  void testFindAllEmpty() {
    Iterable<Customer> foundeCustomerList = customerRepository.findAll();
    List<Customer> emptyArray = new ArrayList<Customer>();
    assertIterableEquals(foundeCustomerList, emptyArray);
  }

  // findAllById()-Tests:
  @Test
  void testFindAllById() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    List<String> idList = new ArrayList<String>();
    idList.add(mats.getId());
    idList.add(thomas.getId());
    Iterable<Customer> found = customerRepository.findAllById(idList);
    List<Customer> customerList = new ArrayList<Customer>();
    customerList.add(mats);
    customerList.add(thomas);
    assertEquals(found, customerList);
  }

  @Test
  void testFindAllByIdNotFound() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    ArrayList<String> idList = new ArrayList<String>();
    idList.add("123");
    idList.add("456");
    idList.add("cantfindthis");
    List<Customer> customerList = new ArrayList<Customer>();
    customerList.add(mats);
    customerList.add(thomas);
    Iterable<Customer> foundById = customerRepository.findAllById(idList);
    assertEquals(foundById, customerList);
  }

  @Test
  void testFindAllByIdNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.findAllById(null);
      }
    );
  }

  @Test
  void testFindAllByIdOneNull() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    ArrayList<String> idList = new ArrayList<String>();
    idList.add("123");
    idList.add("456");
    String idNull = null;
    idList.add(idNull);
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.findAllById(idList);
      }
    );
  }

  // count()-Tests:
  @Test
  void testCountFilledRepo() {
    customerRepository.save(mats);
    customerRepository.save(thomas);
    assertEquals(customerRepository.count(), 2);
  }

  @Test
  void testCountEmptyRepo() {
    CrudRepository<Customer, String> customerRepository = new CustomerRepository();
    assertEquals(customerRepository.count(), 0);
  }

  // deleteById()-Tests
  @Test
  void testDeleteById() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    long before = customerRepository.count();
    customerRepository.deleteById("456");
    assertEquals(customerRepository.count(), before - 1);
    assertFalse(customerRepository.existsById("456"));
  }

  @Test
  void testDeleteByIdNotFound() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    long before = customerRepository.count();
    customerRepository.deleteById("cantfindthis");
    assertEquals(customerRepository.count(), before);
  }

  @Test
  void testDeleteByIdNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.deleteById(null);
      }
    );
  }

  // delete()-Tests
  @Test
  void testDelete() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    long before = customerRepository.count();
    customerRepository.delete(mats);
    assertEquals(customerRepository.count(), before - 1);
    assertFalse(customerRepository.existsById("123"));
  }

  @Test
  void testDeleteNotFound() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    long before = customerRepository.count();
    customerRepository.delete(thomas);
    assertEquals(customerRepository.count(), before);
    assertFalse(customerRepository.existsById("cantfindthis"));
  }

  @Test
  void testDeleteNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.delete(null);
      }
    );
  }

  // deleteAllById()-Tests
  @Test
  void testDeleteAllById() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    long before = customerRepository.count();
    ArrayList<String> idList = new ArrayList<String>();
    idList.add("123");
    long listAmount = idList.size();
    customerRepository.deleteAllById(idList);
    assertEquals(customerRepository.count(), before - listAmount);
    assertFalse(customerRepository.existsById("123"));
    assertTrue(customerRepository.existsById("456"));
  }

  @Test
  void testDeleteAllByIdNotFound() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    long before = customerRepository.count();
    ArrayList<String> idList = new ArrayList<String>();
    idList.add("123");
    idList.add("cantfindthis");
    customerRepository.deleteAllById(idList);
    assertEquals(customerRepository.count(), before - 1);
    assertFalse(customerRepository.existsById("123"));
    assertTrue(customerRepository.existsById("456"));
  }

  @Test
  void testDeleteAllByIdNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.deleteAllById(null);
      }
    );
  }

  @Test
  void testDeleteAllByIdOneNull() {
    mats.setId("123");
    thomas.setId("456");
    customerRepository.save(mats);
    customerRepository.save(thomas);
    ArrayList<String> idList = new ArrayList<String>();
    idList.add("123");
    String idNull = null;
    idList.add(idNull);
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.deleteAllById(idList);
      }
    );
  }

  // deleteAll(Iterable<>entities)-Tests
  @Test
  void testDeleteAllEntities() {
    customerRepository.save(mats);
    customerRepository.save(thomas);
    long before = customerRepository.count();
    ArrayList<Customer> customerList = new ArrayList<Customer>();
    customerList.add(mats);
    long listAmount = customerList.size();
    customerRepository.deleteAll(customerList);
    assertEquals(customerRepository.count(), before - listAmount);
  }

  @Test
  void testDeleteAllEntitiesNotFound() {
    customerRepository.save(mats);
    long before = customerRepository.count();
    ArrayList<Customer> customerList = new ArrayList<Customer>();
    thomas.setId("456");
    customerList.add(thomas);
    customerRepository.deleteAll(customerList);
    assertEquals(customerRepository.count(), before);
  }

  @Test
  void testDeleteAllNull() {
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        customerRepository.deleteAll(null);
      }
    );
  }

  // deleteAll()-Tests
  @Test
  void testDeleteAll() {
    customerRepository.save(mats);
    customerRepository.save(thomas);
    assertEquals(customerRepository.count(), 2);

    customerRepository.deleteAll();
    assertEquals(customerRepository.count(), 0);
  }

  @Test
  void testDeleteAllWhenAlreadyEmpty() {
    long before = customerRepository.count();
    customerRepository.deleteAll();
    long after = customerRepository.count();
    assertEquals(before, after);
    assertEquals(after, 0);
  }
}
