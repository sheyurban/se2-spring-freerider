package de.freerider.repository;

import de.freerider.datamodel.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CustomerRepository_Impl")
public class CustomerRepository implements CrudRepository<Customer, String> {

  //
  private final IDGenerator idGen = new IDGenerator(
    "C",
    IDGenerator.IDTYPE.NUM,
    6
  );
  private Map<String, Customer> customerMap = new HashMap<String, Customer>();

  public <S extends Customer> S save(S entity) {
    Customer newCustomer = entity;
    if (entity == null) {
      throw new IllegalArgumentException();
    } else {
      if (entity.getId() == "" || entity.getId() == null) {
        String newid = idGen.nextId();
        while (customerMap.containsKey(newid)) {
          newid = idGen.nextId();
        }
        entity.setId(newid);
      }
      newCustomer = customerMap.put(entity.getId(), entity);
      if (newCustomer == null) {
        return entity;
      } else {
        return (S) newCustomer;
      }
    }
  }

  public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
    if (entities == null) {
      throw new IllegalArgumentException();
    } else {
      for (S s : entities) {
        if (s == null) continue;
        save(s);
      }
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
    if (ids == null) {
      throw new IllegalArgumentException();
    } else {
      for (String id : ids) {
        if (id == null) {
          throw new IllegalArgumentException();
        } else if (customerMap.containsKey(id)) {
          customerList.add(customerMap.get(id));
        }
      }
      return customerList;
    }
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
    if (entity == null || entity.getId() == null) {
      throw new IllegalArgumentException();
    } else {
      customerMap.remove(entity.getId(), entity);
    }
  }

  public void deleteAllById(Iterable<? extends String> ids) {
    if (ids == null) throw new IllegalArgumentException();
    for (String id : ids) {
      deleteById(id);
    }
  }

  public void deleteAll(Iterable<? extends Customer> entities) {
    if (entities == null) throw new IllegalArgumentException();
    for (Customer entity : entities) {
      delete(entity);
    }
  }

  public void deleteAll() {
    customerMap.clear();
  }
}
