package View.DefaultUI;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import View.LoginUI.LoginWindow;

/**
 * Created by Abeer on 04.06.16.
 * Sub window to confirm user logout
 */
public class LogoutConfirmationWindow extends Window  {

    private Button yesButton = new Button("Yes");
    private Button noButton = new Button("No");
    private HorizontalLayout actions = new HorizontalLayout(yesButton, noButton);
    private VerticalLayout content = new VerticalLayout();

    /**
     * Constructor of the Logout confirmation window
     */
    public LogoutConfirmationWindow() {
        super("User Logout "); // Set window caption
        center();
        actions.setSpacing(true);
        setModal(true);
        setClosable(false);
        setResizable(false);
        content.setMargin(true);
        content.setSpacing(true);
        Label configuration  = new Label("Are you sure you want to log out ");

        yesButton.addClickListener(e -> {
            // close the session and  move to login window
            getUI().getSession().setAttribute("user", null);
            getUI().getSession().setAttribute("databaseName", null);
            getUI().getSession().setAttribute("connectionString", null);
            getUI().getSession().setAttribute("isAdmin",null);
            getUI().getNavigator().navigateTo(LoginWindow.VIEW_NAME);
            close();
        });

        noButton.addClickListener(e -> {
            close();
        });

        content.addComponents(configuration, actions);
        setContent(content);
    }
}
