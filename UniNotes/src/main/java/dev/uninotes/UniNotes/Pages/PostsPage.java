package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NavBar;
import dev.uninotes.UniNotes.Components.PostComponent;
import dev.uninotes.UniNotes.Components.PublishBar;
import dev.uninotes.UniNotes.Database.DatabaseManager;
import dev.uninotes.UniNotes.Post;

import java.util.ArrayList;
import java.util.List;

@Route("posts")
public class PostsPage extends VerticalLayout {

    private VerticalLayout postsLayout;
    private List<Post> posts;
    private int loadedCount = 0;

    public PostsPage() {

        setPadding(false);
        setSpacing(false);
        setSizeFull();

        // Wrapper to center content and add bottom space
        Div wrapper = new Div();
        wrapper.getStyle()
                // Center content and limit max width
                .set("margin", "0 auto")
                .set("max-width", "600px")
                .set("width", "100%")
                .set("display", "flex")
                .set("flex-direction", "column")
                // Add padding bottom so "Load more" does not clash with the fixed NavBar
                .set("padding-bottom", "100px");

        // PublishBar at the top, centered
        PublishBar publishBar = new PublishBar();
        publishBar.getStyle()
                .set("width", "100%")
                .set("display", "flex")
                .set("justify-content", "center");

        wrapper.add(publishBar);

        // Posts layout
        postsLayout = new VerticalLayout();
        postsLayout.setPadding(false);
        postsLayout.setSpacing(false);
        postsLayout.setWidth("100%");
        postsLayout.getStyle()
                .set("box-sizing", "border-box");

        // load and displays the first posts
        loadPosts();
        displayPosts();

        // "Load more" button
        Button loadMoreButton = new Button("Load more", e -> displayPosts());
        loadMoreButton.getStyle().set("margin", "20px auto");

        wrapper.add(postsLayout, loadMoreButton);

        // NavBar fixed at bottom
        NavBar navBar = new NavBar();
        navBar.getStyle()
                .set("position", "fixed")
                .set("bottom", "0")
                .set("left", "0")
                .set("right", "0")
                .set("width", "100%");

        add(wrapper, navBar);
        setFlexGrow(1, wrapper);
    }

    private void displayPosts(){
        //limits the number of the posts displayed per time
        int limit = Math.min(loadedCount + 10, posts.size());

        for (int i = loadedCount; i < limit; i++) {
            Post p = posts.get(i);
            postsLayout.add(new PostComponent(p.getUsernameOfPost(), p.getOwnerProfileImage(), p.getText(), p.getDateTime()));
        }
        //the next time it will load the next posts
        loadedCount = limit;
    }

    private void loadPosts(){
        posts = DatabaseManager.SELECT_POSTS();
    }
}
