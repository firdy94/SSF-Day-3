package ibf.ssf.day3workshop;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day3workshopApplication {

	public static void main(String[] args) {
		SpringApplication contactApp = new SpringApplication(Day3workshopApplication.class);
		ApplicationArguments argOptions = new DefaultApplicationArguments(args);
		List<String> dirArg = argOptions.getOptionValues("dataDir");
		String dir = null;
		if (dirArg != null) {
			File myFile = new File(dirArg.get(0));
			if (!myFile.exists() && !myFile.isDirectory()) {
				myFile.mkdirs();
			}
			dir = myFile.getAbsolutePath();

		} else {
			System.out.println(
					"Directory parameter not set. Plese set the directory using the --dataDir flag during program execution.");
			System.exit(1);
		}
		contactApp.setDefaultProperties(Collections.singletonMap("data.dir", dir));

		contactApp.run(args);

	}

}
