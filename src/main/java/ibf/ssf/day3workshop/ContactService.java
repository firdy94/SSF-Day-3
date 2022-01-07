package ibf.ssf.day3workshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContactService {
	private static final Logger logger = LogManager.getLogger(ContactService.class);

	@Autowired
	Contact contact;

	public void saveContact(Contact contact, String dataDir) throws IOException {
		String fileName = contact.getHexID();
		String name = contact.getName();
		String email = contact.getEmail();
		String phone = contact.getPhone_num();

		logger.info("Name: {}", name);
		logger.info("Email: {}", email);
		logger.info("Phone Number: {}", phone);

		File file = Path.of(dataDir, fileName).toFile();
		logger.info("Filepath {}", file.getAbsolutePath());
		logger.info("File created: {}", file.createNewFile());

		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
			bw.write(name + "\n");
			bw.write(email + "\n");
			bw.write(phone + "\n");
			bw.flush();
		}

	}

	public void getContactById(String id, String dataDir, Model model) {
		File file = Path.of(dataDir, id).toFile();
		logger.info("Filepath {}", file.getAbsolutePath());
		Contact contactResponse = new Contact();
		if (file.exists()) {
			List<String> filelines = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
				String line = "";
				while (null != (line = br.readLine())) {
					filelines.add(line.strip());
				}
				contactResponse.setName(filelines.get(0));
				contactResponse.setEmail(filelines.get(1));
				contactResponse.setPhone_num(filelines.get(2));

				model.addAttribute("contact", contactResponse);
			} catch (FileNotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getContact(Model model) {
		model.addAttribute("contact", contact);
	}

}
