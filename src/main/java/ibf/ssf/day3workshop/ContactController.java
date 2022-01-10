package ibf.ssf.day3workshop;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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
	public ResponseEntity<String> createdForm(@ModelAttribute Contact contact) {
		try {
			contactservice.saveContact(contact, dataDir);
			return new ResponseEntity<>("Record created", HttpStatus.CREATED);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>("Unable to process request", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/contact/{id}")
	public String retrieveForm(@PathVariable String id, @ModelAttribute Contact contact, Model model) {
		logger.info("id: {}", id);

		if (contactservice.checkfilePath(id, dataDir)) {
			contactservice.getContactById(id, dataDir, model);
			return "searchForm";
		} else {
			model.addAttribute("header1", "Record does not exist");
			return "errorForm";

		}

	}

	@GetMapping("/contact/update/{id}")
	public String modifyForm(@PathVariable String id, @ModelAttribute Contact contact,
			Model model) {
		logger.info("id: {}", id);
		contact.setHexID(id);
		if (contactservice.checkfilePath(id, dataDir)) {
			return "updateAddressForm";
		} else {
			model.addAttribute("header1", "File does not exist");
			return "errorForm";

		}
	}

	@PostMapping("/contact/update/{id}")
	public HttpEntity<String> modifiedForm(@PathVariable String id, @ModelAttribute Contact contact) {
		try {
			contactservice.updateContactById(contact, id, dataDir);
			return new ResponseEntity<>("Record updated", HttpStatus.ACCEPTED);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>("Unable to process request", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/contact/delete/{id}")
	public HttpEntity<String> deleteForm(@PathVariable String id) {
		try {
			contactservice.deleteContactById(id, dataDir);
			return new ResponseEntity<>("Record deleted", HttpStatus.ACCEPTED);

		} catch (FileNotFoundException e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>("No such file found", HttpStatus.NOT_FOUND);

		}

	}

}
