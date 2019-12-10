package ICTProject.CistPortal.page.myMessage;

import ICTProject.CistPortal.MySession;
import ICTProject.CistPortal.bean.MessageView;
import ICTProject.CistPortal.page.TemplatePage;
import ICTProject.CistPortal.page.viewMessage.MessageViewListPage;
import ICTProject.CistPortal.page.viewMessage.alreadyReadMessageViewListPage;
import ICTProject.CistPortal.service.IMyMessageService;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.wicketstuff.annotation.mount.MountPath;

import java.time.LocalDateTime;
import java.util.List;


@AuthorizeInstantiation({"ADMIN" , "TEACHER" , "STUDENT"})
@MountPath("MyMessageList")
public class MyMessageListPage extends TemplatePage {

    @SpringBean
    IMyMessageService myMessageService;

    public MyMessageListPage() {
        super();

        IModel<List<MessageView>> messageViewModel = Model.ofList(myMessageService.getMyMessageList(MySession.get().getUserId()));

        ListView<MessageView> MessageViewLV = new ListView<MessageView>("MessageView", messageViewModel) {
            @Override
            protected void populateItem(ListItem<MessageView> listItem) {
                IModel<MessageView> itemModel = listItem.getModel();
                MessageView messageView = itemModel.getObject();

                IModel<String> titleModel = Model.of(messageView.getTitle());
                Label titleLabel = new Label("title", titleModel);
                listItem.add(titleLabel);

                IModel<LocalDateTime> deadLineModel = Model.of(messageView.getDeadLine());
                Label deadlineLabel = new Label("deadLine", deadLineModel);
                listItem.add(deadlineLabel);

                IModel<LocalDateTime> updateDateModel = Model.of(messageView.getUpdateDate());
                Label updateDateLabel = new Label("updateDate", updateDateModel);
                listItem.add(updateDateLabel);

                var label = new Link<>("toMessageViewDetail") {
                    @Override
                    public void onClick() {
                        setResponsePage(new MyMessageDetailPage(messageView.getId()));
                    }
                };
                listItem.add(label);

            }
        };
        add(MessageViewLV);

        add(new BookmarkablePageLink<>("toAlreadyReadMessageViewListPage", alreadyReadMessageViewListPage.class));
        add(new BookmarkablePageLink<>("toMessageViewListPage", MessageViewListPage.class));

    }
}
