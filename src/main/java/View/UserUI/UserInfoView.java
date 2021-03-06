package View.UserUI;

import Model.Entity.User;
import Presenter.UserPresenter;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.codec.digest.Crypt;

/**
 * Created by Abeer on 5/19/2016.
 * class that show sub window contains form for user info
 *
 */
public class UserInfoView extends Window  {
    private VerticalLayout content;

    private TextField firstName;
    private TextField lastName;
    private TextField userName;
    private TextField password;
    private TextField email;

    private CheckBox isAdmin;

    private HorizontalLayout userInfolayout1;
    private HorizontalLayout userInfolayout2;
    private HorizontalLayout userInfolayout3;
    private HorizontalLayout actions;

    private Button save;
    private Button cancel;
    private Button delete;
    private Validator NameValidator;
    private Validator passwordValdiator;

    /**
     * Constructor of the user info view (Edit)
     * @param user the user to display
     * @param userPresenter the presenter of the view that called this sub window (UserPresenter)
     */
   public UserInfoView (User user, UserPresenter userPresenter){
       super("Edit User");

       // init fields and buttons
       init();
       // add validators to the fields
       addValidators();
        // binds the user parameters  to the fields
       if (user.getId()!=-1) {
           BeanFieldGroup.bindFieldsUnbuffered(user, this);
           isAdmin.setValue(user.isAdmin());
       }
        // set actions to save button
       save.addClickListener(e -> {
            // check the validation of the button
           if(validateUserParameter())
               // call userPresenter to update the user info
           {  userPresenter.updateUser(user.getId(),firstName.getValue(),lastName.getValue(),
                       userName.getValue(), Crypt.crypt(password.getValue()),
                       email.getValue(),(boolean)isAdmin.getValue()); close();}
           else{// show error if the parameters are not valid
               new Notification("Enter required parameters", Notification.TYPE_ERROR_MESSAGE)
                       .show(getUI().getPage());
           }

       });
       // set delete button action
       delete.addClickListener(e -> {
           // call userPresenter to delete user
           userPresenter.deleteUser(user.getUsername(),user.getPassword());
            close();
       });
   }

    /**
     * Constructor of the userInfoView (Creation)
     * @param userPresenter the presenter of the view that called this sub window (UserPresenter)
     */
    public UserInfoView(UserPresenter userPresenter){
        super("New User");

        // init fields and buttons
        init();
        addValidators();
        // make delete button hidden
        delete.setVisible(false);
        // set save button action
        save.addClickListener(e -> {
            // check the validation of user info
            if(validateUserParameter())
                // call userPresenter to create new user
            {  userPresenter.addNewUser(firstName.getValue(),lastName.getValue(),
                        userName.getValue(),password.getValue(),
                        email.getValue(),isAdmin.getValue());close();}
            else{
                // show error if the parameters are not valid
                new Notification("Enter required parameters", Notification.TYPE_ERROR_MESSAGE)
                        .show(getUI().getPage());
            }
               });
    }

    /**
     * Method to validate the user parameters
     * @return true if the parameters are correct, false otherwise
     */
    public boolean validateUserParameter() {
        return (firstName.isValid()&&lastName.isValid()& userName.isValid()&&
                isValid(email.getValue())&&password.isValid());
    }

    /**
     * Method to check if the oject is valid
     * @param value the object to check
     * @return true if teh object is valid, false otherwise
     */
    public boolean isValid(Object value) {
        if (value==null||value.toString().trim().isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    /* Method to add validators */
    private void addValidators(){
        NameValidator =new StringLengthValidator("Name must be 3-25 characters", 3, 25, true);
        passwordValdiator=new StringLengthValidator("Password must be 6-10 characters", 6, 10, true);

        firstName.addValidator(NameValidator);
        lastName.addValidator(NameValidator);
        password.addValidator(passwordValdiator);

        firstName.setRequired(true);firstName.setImmediate(true);
        lastName.setRequired(true);lastName.setImmediate(true);
        userName.setRequired(true);userName.setImmediate(true);
        password.setRequired(true);password.setImmediate(true);
        email.setRequired(true);password.setImmediate(true);
    }

    /**
     * Method to init the view (called when launched)
     */
    private void init(){
        content = new VerticalLayout();

        firstName = new TextField("FirstName");
        lastName = new TextField("lastName");
        userName = new TextField("Username");
        password = new TextField("Password");
        email= new TextField("Email");

        isAdmin = new CheckBox("Admin");
        isAdmin.setImmediate(true);

        cancel= new Button("Cancel");
        save = new Button("Save", FontAwesome.EDIT);
        delete = new Button("Delete",FontAwesome.TRASH_O);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        delete.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        userInfolayout1 = new HorizontalLayout(firstName,lastName);
        userInfolayout2 = new HorizontalLayout(userName,password);
        userInfolayout3 = new HorizontalLayout(email,isAdmin);
        actions = new HorizontalLayout(save, delete,cancel);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        userInfolayout1.setSpacing(true);
        userInfolayout2.setSpacing(true);
        userInfolayout3.setSpacing(true);
        actions.setSpacing(true);

        setModal(true);
        center();
        setClosable(true);
        setResizable(true);
        setWidth("40%");
        setHeight("60%");

        cancel.addClickListener(e -> {
            close();
        });
        content.setSpacing(true);
        content.setMargin(true);
        content.addComponents(userInfolayout1,userInfolayout2,userInfolayout3,actions);
        setContent(content);
    }
}

