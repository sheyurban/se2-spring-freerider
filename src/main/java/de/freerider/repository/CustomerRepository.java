package de.freerider.repository;

import de.freerider.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository implements CrudRepository<Customer, String> {

  //
  private final IDGenerator idGen = new IDGenerator(
    "C",
    IDGenerator.IDTYPE.NUM,
    6
  );
  private Map<String, Customer> customerMap = new HashMap<String, Customer>();

  public <S extends Customer> S save(S entity) {
    if (entity.getId() == "" || entity.getId() == null) {
      String newId = idGen.nextId();
      while (customerMap.containsKey(newId)) {
        newId = idGen.nextId();
      }
      entity.setId(newId);
      customerMap.put(entity.getId(), entity);
    } else if (entity.getId() != "" || entity.getId() != null) {
      if (customerMap.containsKey(entity.getId())) {
        throw new IllegalArgumentException("This customer already exists.");
      }
      customerMap.put(entity.getId(), entity);
    } else {
      throw new IllegalArgumentException();
    }
    return entity;
  }

  public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
    for (S s : entities) {
      save(s);
    }
    return entities;
  }

  public Optional<Customer> findById(String id) {
    Optional<Customer> customer = Optional.empty();
    if (id != null) {
      customer = Optional.ofNullable(customerMap.get(id));
    } else {
      throw new IllegalArgumentException();
    }
    return customer;
  }

  public boolean existsById(String id) {
    boolean result = false;
    if (id == null) {
      throw new IllegalArgumentException();
    } else {
      result = customerMap.containsKey(id);
    }
    return result;
  }

  public Iterable<Customer> findAll() {
    return customerMap.values();
  }

  public Iterable<Customer> findAllById(Iterable<String> ids) {
    ArrayList<Customer> customerList = new ArrayList<Customer>();
    for (String id : ids) {
      if (id == null) {
        throw new IllegalArgumentException();
      } else if (customerMap.containsKey(id)) {
        customerList.add(customerMap.get(id));
      }
    }
    return customerList;
  }

  public long count() {
    return customerMap.size();
  }

  public void deleteById(String id) {
    if (id == null) {
      throw new IllegalArgumentException();
    } else {
      customerMap.remove(id);
    }
  }

  public void delete(Customer entity) {
    if (entity == null) {
      throw new IllegalArgumentException();
    } else {
      customerMap.remove(entity.getId(), entity);
    }
  }

  public void deleteAllById(Iterable<? extends String> ids) {
    for (String id : ids) {
      deleteById(id);
    }
  }

  public void deleteAll(Iterable<? extends Customer> entities) {
    for (Customer entity : entities) {
      delete(entity);
    }
  }

  public void deleteAll() {
    customerMap.clear();
  }
}
