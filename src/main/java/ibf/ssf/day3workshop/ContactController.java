package ibf.ssf.day3workshop;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {
	private static final Logger logger = LogManager.getLogger(ContactController.class);

	@Autowired
	ContactService contactservice;

	@Value("${data.dir}")
	private String dataDir;

	@GetMapping("/contact")
	public String contactForm(Model model) {
		contactservice.getContact(model);
		return "addressForm";
	}

	@PostMapping("/contact")
	public ResponseEntity<String> createdForm(@ModelAttribute Contact contact) throws IOException {
		contactservice.saveContact(contact, dataDir);
		return new ResponseEntity<>("Record created", HttpStatus.CREATED);
	}

	@GetMapping("/contact/{id}")
	public String retrieveForm(@PathVariable String id, @ModelAttribute Contact contact, Model model) {
		logger.info("id: {}", id);
		contactservice.getContactById(id, dataDir, model);
		return "searchForm";
	}

}
