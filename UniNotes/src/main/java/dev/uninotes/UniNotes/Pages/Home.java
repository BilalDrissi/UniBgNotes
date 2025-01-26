package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Utils.Utils;

@Route("home")
public class Home {

    public Home() {

        Utils.redirectToLoginIfNotLoggedIn();


    }

}
