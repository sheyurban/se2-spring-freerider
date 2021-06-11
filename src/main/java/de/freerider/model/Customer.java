package de.freerider.model;

public class Customer {

  private String id;
  private String lastName;
  private String firstName;
  private String contact;
  private Status status;

  public enum Status {
    New,
    InRegistration,
    Active,
    Suspended,
    Deleted,
  }

  public Customer(String lastName, String firstName, String contact) {
    this.id = null;
    this.lastName = lastName;
    this.firstName = firstName;
    this.contact = contact;
    this.status = Status.New;
  }

  public Customer() {
    this.id = null;
    this.lastName = "";
    this.firstName = "";
    this.contact = "";
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
    if (id != null && this.id != null) {
      this.id = getId();
    } else {
      this.id = id;
    }
  }

  public void setLastName(String lastName) {
    if (lastName == null) {
      lastName = "";
    }
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    if (firstName == null) {
      firstName = "";
    }
    this.firstName = firstName;
  }

  public void setContact(String contact) {
    if (contact == null) {
      contact = "";
    }
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
