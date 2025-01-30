package dev.uninotes.UniNotes;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class RootRoute extends VerticalLayout {

    public RootRoute() {
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI.getCurrent().navigate("login");
    }


}
