package ICTProject.CistPortal.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class MessageCreateCompPage extends WebPage {

    public MessageCreateCompPage() {

        add(new BookmarkablePageLink<Void>("toHomePage",HomePage.class));

    }
}