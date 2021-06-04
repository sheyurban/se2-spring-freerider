package de.freerider.model;

public class Customer {

  private String id;
  private String lastName;
  private String firstName;
  private String contact;
  private Status status;

  private enum Status {
    New,
    InRegistration,
    Active,
    Suspended,
    Deleted,
  }

  public Customer(String lastName, String firstName, String contact) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.contact = contact;
    this.id = null;
    this.status = Status.New;
  }

  public String getId() {
    return id;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getContact() {
    return contact;
  }

  public Status getStatus() {
    return status;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "\nCustomer: " + id + " (" + firstName + " " + lastName + ")";
  }
}
