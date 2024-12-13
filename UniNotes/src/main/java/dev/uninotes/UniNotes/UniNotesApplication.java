package dev.uninotes.UniNotes;

import com.vaadin.flow.component.UI;
import dev.uninotes.UniNotes.Login.LoginPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.vaadin.flow.component.UI.*;

@SpringBootApplication
public class UniNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniNotesApplication.class, args);

		LoginPage loginPage = new LoginPage();
	}

}

