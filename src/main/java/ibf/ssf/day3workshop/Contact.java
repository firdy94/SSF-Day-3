package ibf.ssf.day3workshop;

import java.io.Serializable;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class Contact implements Serializable {
	private String name;
	private String email;
	private String phone_num;

	private final String hexID;

	public Contact() {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(0x1000000);
		String formattedHex = String.format("%08x", num);
		this.hexID = formattedHex;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_num() {
		return this.phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getHexID() {
		return this.hexID;
	}

}
