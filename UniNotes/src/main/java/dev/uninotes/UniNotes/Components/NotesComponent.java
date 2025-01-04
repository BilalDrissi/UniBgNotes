package dev.uninotes.UniNotes.Components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

public class NotesComponent extends HorizontalLayout {

    public NotesComponent(String username, String dataPubblicazione, String descrizione, String urlImmagineCopertina) {



        Image coverImage = new Image("/images/prova.png", "Notes Image");
       // add(image);

        // Immagine di copertina (rettangolare)
       // Image coverImage = new Image(urlImmagineCopertina, "Copertina");
        coverImage.setWidth("100px");   // regola a piacere
        coverImage.setHeight("60px");   // regola a piacere

        // Layout per il testo (username, data, descrizione)
        VerticalLayout textLayout = new VerticalLayout();
        textLayout.setPadding(false);
        textLayout.setSpacing(false);

        // Riga per username e data
        HorizontalLayout topRow = new HorizontalLayout();
        topRow.setWidthFull();
        topRow.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span usernameSpan = new Span(username);
        // Metti in grassetto lo username
        usernameSpan.getStyle().set("font-weight", "bold");

        Span dataSpan = new Span(dataPubblicazione);
        // Allineato a destra rispetto all'username
        topRow.add(usernameSpan, dataSpan);

        // Descrizione
        Span descrizioneSpan = new Span(descrizione);

        // Aggiungi i componenti al layout di testo
        textLayout.add(topRow, descrizioneSpan);

        // Aggiungi immagine e blocco di testo al layout principale
        add(coverImage, textLayout);

        // Stile generale del layout
        setSpacing(true);
        setWidthFull();
        setAlignItems(Alignment.START);
    }
}
