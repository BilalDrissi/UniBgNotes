package dev.uninotes.UniNotes.Pages;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import dev.uninotes.UniNotes.Components.NavBar;
import dev.uninotes.UniNotes.Components.PostComponent;
import dev.uninotes.UniNotes.Components.PublishBar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route("posts")
public class PostsPage extends VerticalLayout {

    private VerticalLayout postsLayout;
    private List<PostData> allPosts;
    private int loadedCount = 0;
    private final int PAGE_SIZE = 5;

    public PostsPage() {
        // Basic page setup
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

        // Generate mock posts
        allPosts = generateMockPosts();
        loadMorePosts(); // Load initial posts

        // "Load more" button
        Button loadMoreButton = new Button("Load more", e -> loadMorePosts());
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

    /**
     * Loads more posts by appending the next PAGE_SIZE items, if available.
     */
    private void loadMorePosts() {
        int end = Math.min(loadedCount + PAGE_SIZE, allPosts.size());
        if (end <= loadedCount) {
            return; // No more posts to load
        }

        for (int i = loadedCount; i < end; i++) {
            PostData data = allPosts.get(i);
            PostComponent post = new PostComponent(data.username, data.profileImage, data.comment, data.postTime);
            postsLayout.add(post);
        }
        loadedCount = end;
    }

    /**
     * Generates mock posts for demonstration purposes.
     */
    private List<PostData> generateMockPosts() {
        List<PostData> posts = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            posts.add(new PostData("User" + i, "https://via.placeholder.com/50",
                    "This is a sample post number " + i + ".", LocalDateTime.now().minusHours(i)));
        }
        return posts;
    }

    /**
     * Internal class to represent post data.
     */
    private static class PostData {
        String username;
        String profileImage;
        String comment;
        LocalDateTime postTime;

        public PostData(String username, String profileImage, String comment, LocalDateTime postTime) {
            this.username = username;
            this.profileImage = profileImage;
            this.comment = comment;
            this.postTime = postTime;
        }
    }
}
