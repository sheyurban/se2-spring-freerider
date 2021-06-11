package de.freerider.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.freerider.model.Customer.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTest {

  //
  private static int loglevel = 1; // 0: silent; 1: @Test methods; 2: all methods

  private Customer mats;
  private Customer thomas;

  // Constructor
  public CustomerTest() {
    log("Constructor()", "CustomerTest() called");
    mats = new Customer("", "", "");
    thomas = new Customer();
  }

  @Test
  void testIdNull() {
    String id = mats.getId();
    assertNull(id);
  }

  @Test
  void testSetId() {
    String id = "123";
    mats.setId(id);
    String idMats = mats.getId();
    assertEquals(idMats, id);
  }

  @Test
  void testSetIdOnlyOnce() {
    String id = "123";
    mats.setId(id);
    String id2 = "456";
    mats.setId(id2);
    String idMats = mats.getId();
    assertEquals(idMats, id);
  }

  @Test
  void testResetId() {
    String id = "123";
    mats.setId(id);
    mats.setId(null);
    String idMats = mats.getId();
    assertNull(idMats);
  }

  //   ___________________________________________________________________________

  @Test
  void testNamesInitial() {
    String firstname = thomas.getFirstName();
    assertEquals(firstname, "");
    assertNotNull(firstname);
    String lastname = thomas.getLastName();
    assertEquals(lastname, "");
    assertNotNull(lastname);
  }

  @Test
  void testNamesSetNull() {
    mats.setFirstName(null);
    mats.setLastName(null);
    String firstname = mats.getFirstName();
    assertNotNull(firstname);
    assertEquals(firstname, "");
    String lastname = mats.getLastName();
    assertNotNull(lastname);
    assertEquals(lastname, "");
  }

  @Test
  void testSetNames() {
    mats.setFirstName("Mats");
    mats.setLastName("Matsen");
    String firstname = mats.getFirstName();
    assertNotNull(firstname);
    assertEquals(firstname, "Mats");
    String lastname = mats.getLastName();
    assertNotNull(lastname);
    assertEquals(lastname, "Matsen");
  }

  //   ___________________________________________________________________________

  @Test
  void testContactsInitial() {
    String contact = thomas.getContact();
    assertNotNull(contact);
    assertEquals(contact, "");
  }

  @Test
  void testContactsSetNull() {
    thomas.setContact(null);
    String contact = thomas.getContact();
    assertNotNull(contact);
    assertEquals(contact, "");
  }

  @Test
  void testSetContact() {
    thomas.setContact("thomas@mueller.de");
    String contact = thomas.getContact();
    assertNotNull(contact);
    assertEquals(contact, "thomas@mueller.de");
  }

  //   ___________________________________________________________________________

  @Test
  void testStatusInitial() {
    Status statusMats = mats.getStatus();
    Status statusThomas = thomas.getStatus();
    assertEquals(statusMats, Status.New);
    assertEquals(statusThomas, Status.New);
  }

  @Test
  void testSetStatus() {
    mats.setStatus(Status.InRegistration);
    Status statusMats1 = mats.getStatus();
    assertEquals(statusMats1, Status.InRegistration);

    thomas.setStatus(Status.Active);
    Status statusThomas1 = thomas.getStatus();
    assertEquals(statusThomas1, Status.Active);

    mats.setStatus(Status.Suspended);
    Status statusMats2 = mats.getStatus();
    assertEquals(statusMats2, Status.Suspended);

    thomas.setStatus(Status.Deleted);
    Status statusThomas2 = thomas.getStatus();
    assertEquals(statusThomas2, Status.Deleted);

    mats.setStatus(Status.New);
    Status statusMats3 = mats.getStatus();
    assertEquals(statusMats3, Status.New);
  }

  /*
   * private methods
   */

  private static void log(String label, String meth) {
    if (loglevel >= 2) {
      if (label.equals("@BeforeEach")) {
        System.out.println();
      }
      java.io.PrintStream out_ = System.out;
      if (label.equals("@BeforeAll") || label.equals("@AfterAll")) {
        System.out.println();
        out_ = System.err; // print in red color
      }
      out_.println(
        label + ": " + CustomerTest.class.getSimpleName() + "." + meth
      );
    }
  }
}
