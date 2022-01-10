package ibf.ssf.day3workshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Day3workshopApplicationTests {

	// @Test
	// void contextLoads() {
	// }

	@Test
	public void checkContact() {
		Contact contact = new Contact();
		contact.setName("Firdaus");
		contact.setEmail("firdy94@mail.com");
		contact.setPhone_num("9123456");
		assertEquals("firdy94@mail.com", contact.getEmail(), "Message does not match");
		assertEquals("Firdaus", contact.getName(), "Name does not match");
		assertEquals("9123456", contact.getPhone_num(), "Phone number does not match");

	}
}
